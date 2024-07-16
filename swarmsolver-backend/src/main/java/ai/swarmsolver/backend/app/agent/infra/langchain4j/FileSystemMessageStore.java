package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.langchain4j.data.message.*;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import ai.swarmsolver.backend.app.agent.domain.AgentWorkSpace;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileSystemMessageStore implements ChatMemoryStore {

    private final DirectoryStructure directoryStructure;

    private final ObjectMapper objectMapper;

    public FileSystemMessageStore(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new ChatMessageModule());
    }

    @Override
    @SneakyThrows
    public List<ChatMessage> getMessages(Object key) {
        AgentCoordinate agentCoordinate = (AgentCoordinate) key;
        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, agentCoordinate);
        File agentChatHistoryFile = agentWorkSpace.getAgentChatHistoryFile();
        if (!agentChatHistoryFile.exists()) this.deleteMessages(key);
        return objectMapper.readValue(agentChatHistoryFile, objectMapper.getTypeFactory().constructCollectionType(List.class, ChatMessage.class));
    }

    @Override
    @SneakyThrows
    public void updateMessages(Object key, List<ChatMessage> list) {
        AgentCoordinate agentCoordinate = (AgentCoordinate) key;
        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, agentCoordinate);
        File agentChatHistoryFile = agentWorkSpace.getAgentChatHistoryFile();
        objectMapper.writeValue(agentChatHistoryFile, list);
    }

    @Override
    public void deleteMessages(Object key) {
        AgentCoordinate agentCoordinate = (AgentCoordinate) key;
        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, agentCoordinate);
        updateMessages(key, new ArrayList<>());
    }
}
