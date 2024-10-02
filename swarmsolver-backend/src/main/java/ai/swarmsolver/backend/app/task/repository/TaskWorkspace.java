package ai.swarmsolver.backend.app.task.repository;

import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.Getter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class TaskWorkspace {

    private final DirectoryStructure directoryStructure;

    private final TaskCoordinate taskCoordinate;

    public TaskWorkspace(DirectoryStructure directoryStructure, TaskCoordinate taskCoordinate) {
        this.directoryStructure = directoryStructure;
        this.taskCoordinate = taskCoordinate;
    }

    static public TaskWorkspace of(DirectoryStructure directoryStructure, TaskCoordinate taskCoordinate) {
        return new TaskWorkspace(directoryStructure, taskCoordinate);
    }

    public File getMainTasksDir() {
        return new File(directoryStructure.getDataDir(taskCoordinate.getWorkSpaceName()),  "tasks");
    }

    public File getMainTaskFileLocation() {
        return new File(getMainTasksDir() + File.separator + taskCoordinate.getMainTaskId().getIdentifier() + ".json");
    }

    public List<File> listMainTaskFileLocations() {
        File[] files = getMainTasksDir().listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            return Arrays.asList(files);
        }
        return Collections.emptyList();
    }

    public File getMainTaskDir() {
        return new File(getMainTasksDir(), taskCoordinate.getMainTaskId().getIdentifier());
    }

    public File getSubTaskDir() {
        return new File(new File(getMainTasksDir(), taskCoordinate.getMainTaskId().getIdentifier()), taskCoordinate.getSubTaskId().getIdentifier());
    }

    public File getWorkspaceResourcesDir() {
        return new File(directoryStructure.getDataDir(taskCoordinate.getWorkSpaceName()), "resources");
    }

}
