package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import dev.langchain4j.service.tool.ToolExecutor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LangChain4jAgent implements Agent {

    private final ChatLanguageModel chatLanguageModel;

    private final ChatMemory chatMemory;

    private final String systemMessage;

    private final List<Object> tools;

    private final AgentState agentState;

    private final AgentConversationAccess conversationAccess;

    private final AgentPersistenceAccess persistenceAccess;

    @Builder
    public LangChain4jAgent(ChatLanguageModel languageModel, ChatMemory chatMemory, String systemMessage, AgentState agentState, AgentConversationAccess conversationAccess, AgentPersistenceAccess persistenceAccess, List<Object> tools) {
        this.chatLanguageModel = languageModel;
        this.chatMemory = chatMemory;
        this.agentState = agentState;
        this.systemMessage = systemMessage;
        this.conversationAccess = conversationAccess;
        this.persistenceAccess = persistenceAccess;
        this.tools = tools;
    }

    @Override
    public ConversationCoordinate getConversationCoordinate() {
        return agentState.getConversationCoordinate();
    }

    @Override
    public AgentCoordinate getAgentCoordinate() {
        return agentState.getAgentCoordinate();
    }

    @Override
    public void handleMessage(String message) {
        initIfNeeded();
        UserMessage userMessage = new UserMessage(message);
        chatMemory.add(userMessage);
        conversationAccess.logUserMessage(getConversationCoordinate(), userMessage);
        run();
    }

    private ToolManager toolManager;

    private void initIfNeeded() {
        if (tools != null) {
            toolManager = new ToolManager(tools);
        }

        if (!agentState.isInitialized()) {
            addSystemMessage();
            agentState.setInitialized(true);
            persistenceAccess.persist(agentState);
        }
    }

    private void addSystemMessage() {
        if (agentState.getSystemMessage() != null) {
            chatMemory.add(new SystemMessage(systemMessage));
        }
    }

    private void run() {
        int count = 0;
        while(count < 5) {
            Response<AiMessage> response =  chatLanguageModel.generate(chatMemory.messages(), toolManager != null? toolManager.getToolSpecifications() : null);
            chatMemory.add(response.content());
            conversationAccess.logAiMessageResponse(getConversationCoordinate(), response);

            List<ToolExecutionRequest> toolExecutionRequests = (response.content()).toolExecutionRequests();
            if (toolExecutionRequests == null) {
                return;
            }
            for(ToolExecutionRequest toolExecutionRequest: toolExecutionRequests) {
                ToolExecutionResultMessage toolExecutionResultMessage = executeTool(toolExecutionRequest);
                conversationAccess.logToolExecutionResultMessage(getConversationCoordinate(), toolExecutionResultMessage);
                chatMemory.add(toolExecutionResultMessage);
            }
            count++;
        }
    }

    private ToolExecutionResultMessage executeTool(ToolExecutionRequest toolExecutionRequest) {
        String toolExecutionResult  = toolManager.execute(toolExecutionRequest);
        return ToolExecutionResultMessage.toolExecutionResultMessage(toolExecutionRequest, toolExecutionResult);
    }

}
