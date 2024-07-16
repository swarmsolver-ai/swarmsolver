package ai.swarmsolver.backend.app.conversation;

import ai.swarmsolver.backend.app.task.repository.TaskWorkspace;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;

public class ConversationWorkSpace {

    private final TaskWorkspace taskWorkspace;

    private final ConversationId conversationId;

    public ConversationWorkSpace(TaskWorkspace taskWorkspace, ConversationId conversationId) {
        this.taskWorkspace = taskWorkspace;
        this.conversationId = conversationId;
    }

    public static ConversationWorkSpace of(DirectoryStructure directoryStructure, ConversationCoordinate conversationCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, conversationCoordinate.getTaskCoordinate());
        return new ConversationWorkSpace(taskWorkspace, conversationCoordinate.getConversationId());

    }

    public File getConversationFile() {
        return new File(taskWorkspace.getSubTaskDir(), conversationId.getIdentifier() + ".json");
    }



}
