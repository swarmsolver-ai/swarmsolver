package ai.swarmsolver.backend.app.conversation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.repository.TaskWorkspace;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {

    private final DirectoryStructure directoryStructure;

    private final ObjectMapper objectMapper;

    public ConversationService(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public void add(ConversationCoordinate conversationCoordinate, Message message) {
        SseEmitterManager.sendSseEventToClients(conversationCoordinate.getConversationId().getIdentifier(), objectMapper.writeValueAsString(message));
        addMessage(conversationCoordinate, message);
    }

    public List<String> read(ConversationCoordinate conversationCoordinate) {
        try {
            ConversationWorkSpace conversationWorkSpace = ConversationWorkSpace.of(directoryStructure, conversationCoordinate);
            return Files.readAllLines(conversationWorkSpace.getConversationFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException("problem reading conversation file ", e);
        }
    }


    @SneakyThrows
    public ConversationCoordinate initConversation(TaskCoordinate taskCoordinate) {
        ConversationId conversationId = ConversationId.of("conversation-"+UUID.randomUUID());
        ConversationCoordinate conversationCoordinate = ConversationCoordinate.of(taskCoordinate, conversationId);
        ConversationWorkSpace conversationWorkSpace = ConversationWorkSpace.of(directoryStructure, conversationCoordinate);
        Files.createFile(conversationWorkSpace.getConversationFile().toPath());
        return conversationCoordinate;
    }

    private void addMessage(ConversationCoordinate conversationCoordinate, Message message) {
        ConversationWorkSpace conversationWorkSpace = ConversationWorkSpace.of(directoryStructure, conversationCoordinate);
        String filePath = conversationWorkSpace.getConversationFile().getAbsolutePath();
        try {
            boolean isEmptyFile = Files.size(Path.of(filePath)) == 0;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                if (!isEmptyFile) writer.newLine();
                writer.write(objectMapper.writeValueAsString(message));
            }
        } catch (IOException e) {
            throw new RuntimeException("problem writing conversation file ", e);
        }
    }

}
