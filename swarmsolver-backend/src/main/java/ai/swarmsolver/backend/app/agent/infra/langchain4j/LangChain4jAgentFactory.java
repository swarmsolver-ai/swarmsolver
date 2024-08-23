package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.tools.UserProxyTool;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.ToolWithWorkspaceAccess;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.WorkspaceAccess;
import ai.swarmsolver.backend.app.agent.infra.persistence.AgentPersistenceAccessImpl;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class LangChain4jAgentFactory implements AgentFactory<LangChain4jAgent, LangChain4jAgentSpecification> {

    private final AgentConversationAccess conversationAccess;

    private final AgentRepository agentRepository;
    
    private final FileSystemMessageStore fileSystemMessageStore;

    private final DirectoryStructure directoryStructure;

    public LangChain4jAgentFactory(AgentConversationAccess conversationAccess, AgentRepository agentRepository, FileSystemMessageStore fileSystemMessageStore, DirectoryStructure directoryStructure) {
        this.conversationAccess = conversationAccess;
        this.agentRepository = agentRepository;
        this.fileSystemMessageStore = fileSystemMessageStore;
        this.directoryStructure = directoryStructure;
    }

    @Override
    public Class<LangChain4jAgent> agentClass() {
        return LangChain4jAgent.class;
    }

    @Override
    public LangChain4jAgent createAgent(LangChain4jAgentSpecification agentSpecification, AgentState agentState) {

        List<Object> tools = new ArrayList<>();
        //tools.add(new UserProxyTool());
        List<Object> toolSpecifications = agentSpecification.getToolSpecifications();
        if (toolSpecifications != null) {
            WorkspaceAccess workspaceAccess = new WorkspaceAccess(AgentWorkSpace.of(directoryStructure, agentState.getAgentCoordinate()));
            setWorkspaceAccessForTools(toolSpecifications, workspaceAccess);
            tools.addAll(toolSpecifications);
        }

        AgentCoordinate agentCoordinate = agentState.getAgentCoordinate();
        MessageWindowChatMemory chatMemory = new MessageWindowChatMemory.Builder()
            .chatMemoryStore(fileSystemMessageStore)
            .id(agentCoordinate)
            .maxMessages(100)
            .build();
        ChatLanguageModel languageModel = agentSpecification.getChatModelSupplier().create();
        AgentPersistenceAccessImpl persistenceAccess = new AgentPersistenceAccessImpl(agentCoordinate, agentRepository);

        log.info("system message " + agentSpecification.getSystemMessage());

        return LangChain4jAgent.builder()
                .languageModel(languageModel)
                .chatMemory(chatMemory)
                .systemMessage(agentSpecification.getSystemMessage())
                .tools(tools)
                .agentState(agentState)
                .conversationAccess(conversationAccess)
                .persistenceAccess(persistenceAccess)
                .build();
    }

    private void setWorkspaceAccessForTools(List<Object> tools, WorkspaceAccess workspaceAccess) {
        for (Object tool : tools) {
            if (tool instanceof ToolWithWorkspaceAccess) {
                ((ToolWithWorkspaceAccess) tool).setWorkspaceAccess(workspaceAccess);
            }
        }
    }

}
