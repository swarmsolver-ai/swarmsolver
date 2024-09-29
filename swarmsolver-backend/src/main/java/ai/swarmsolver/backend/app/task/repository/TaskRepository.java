package ai.swarmsolver.backend.app.task.repository;

import ai.swarmsolver.backend.app.task.dto.*;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRepository {

    private ObjectMapper objectMapper;

    private final DirectoryStructure directoryStructure;

    public TaskRepository(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        initObjectMapper();
    }

    private void initObjectMapper() {
        objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @SneakyThrows
    public void store(TaskCoordinate taskCoordinate, Task task) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        objectMapper.writeValue(taskWorkspace.getMainTaskFileLocation(), task);
    }

    @SneakyThrows
    public Task fetch(TaskCoordinate taskCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        return objectMapper.readValue(taskWorkspace.getMainTaskFileLocation(), Task.class);
    }


    @SneakyThrows
    public TaskSummaryListDTO getMainTaskSummaries(String workspace, FilterDTO filterDTO, SortingDTO sortingDTO) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, TaskCoordinate.of(workspace, null));

        List<TaskSummaryDTO> summaries = taskWorkspace.listMainTaskFileLocations().stream()
                .map(this::getSummary)
                .filter(summary -> filter(summary, filterDTO))
                .sorted(getComparator(sortingDTO))
                .toList();

        return TaskSummaryListDTO.builder()
                .workspace(workspace)
                .summaries(summaries)
                .filtering(filterDTO)
                .sorting(sortingDTO)
                .build();


    }

    private boolean filter(TaskSummaryDTO summary, FilterDTO filterDTO) {
        return
                // when filter is off return only non archived, otherwise both archived and non archived
                (filterDTO.isArchived() || !summary.isArchived())
                // when filter on favorites return only favorites, otherwise both favorites and non favorites
                && (!filterDTO.isFavorite() || summary.isFavorite())
                // when filter on name filter on partial matches
                && ( (filterDTO.getName()==null || filterDTO.getName().isEmpty()) || summary.getTitle().contains(filterDTO.getName()))
                ;
    }

    private Comparator<TaskSummaryDTO> getComparator(SortingDTO sortingDTO) {
        Comparator<TaskSummaryDTO> comparator = switch (sortingDTO.getField()) {
            case NAME -> Comparator.comparing(TaskSummaryDTO::getTitle);
            case CREATED -> Comparator.comparing(TaskSummaryDTO::getCreated);
        };
        return (sortingDTO.getOrder() == SortingOrder.DESCENDING)
            ? comparator.reversed()
            : comparator;
    }

    @SneakyThrows
    private TaskSummaryDTO getSummary(File file) {
        Task task = objectMapper.readValue(file, Task.class);
        Path path = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

        FileTime creationTime = attributes.creationTime();
        FileTime lastModifiedTime = attributes.lastModifiedTime();

        LocalDateTime created = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime lastUpdated = LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());

        return TaskSummaryDTO.builder()
                .id(task.getId().getIdentifier())
                .title(task.getTitle())
                .archived(task.isArchived())
                .favorite(task.isFavorite())
                .created(created)
                .lastUpdated(lastUpdated)
                .build();
    }

    private Task findSubTask(Task task, TaskId taskId) {
        if (task.getId().equals(taskId)) {
            return task;
        }
        if (task.getSubTasks() != null) {
            for(Task subTask : task.getSubTasks()) {
                if (findSubTask(subTask, taskId) != null) {
                    return subTask;
                }
            }
        }
        return null;
    }

    public void updateTaskTitle(TaskCoordinate taskCoordinate, String title) {
        Task task = fetch(taskCoordinate);
        task.setTitle(title);
        this.store(taskCoordinate, task);
    }

    public void updateSubTaskTitle(TaskCoordinate taskCoordinate, String title) {
        Task mainTask = fetch(taskCoordinate);
        Task subTask = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        subTask.setTitle(title);
        this.store(taskCoordinate, mainTask);
    }

    public void updateTaskArchived(TaskCoordinate taskCoordinate, boolean archived) {
        Task task = fetch(taskCoordinate);
        task.setArchived(archived);
        this.store(taskCoordinate, task);
    }

    public void updateTaskFavorite(TaskCoordinate taskCoordinate, boolean favorite) {
        Task task = fetch(taskCoordinate);
        task.setFavorite(favorite);
        this.store(taskCoordinate, task);
    }


}
