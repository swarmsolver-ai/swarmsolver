package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.tool.model.ToolExecutor;
import ai.swarmsolver.backend.app.tool.service.ToolService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LangChain4jAgent implements Agent {

    private final ChatLanguageModel chatLanguageModel;

    private final ChatMemory chatMemory;

    private final String systemMessage;

    private final List<ToolSpecification> tools;

    private final ToolService toolService;

    private final AgentState agentState;

    private final AgentConversationAccess conversationAccess;

    private final AgentPersistenceAccess persistenceAccess;

    @Builder
    public LangChain4jAgent(ChatLanguageModel languageModel, ChatMemory chatMemory, String systemMessage, AgentState agentState, AgentConversationAccess conversationAccess, AgentPersistenceAccess persistenceAccess, List<ToolSpecification> tools, ToolService toolService) {
        this.chatLanguageModel = languageModel;
        this.chatMemory = chatMemory;
        this.agentState = agentState;
        this.systemMessage = systemMessage;
        this.conversationAccess = conversationAccess;
        this.persistenceAccess = persistenceAccess;
        this.tools = tools;
        this.toolService = toolService;
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


    private void initIfNeeded() {
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
            Response<AiMessage> response =  chatLanguageModel.generate(chatMemory.messages(), tools);
            chatMemory.add(response.content());
            conversationAccess.logAiMessageResponse(getConversationCoordinate(), response);

            ToolExecutionRequest toolExecutionRequest = (response.content()).toolExecutionRequest();
            if (toolExecutionRequest == null) {
                // log.info(response.content().text());
                return;
            }
            ToolExecutionResultMessage toolExecutionResultMessage = executeTool(toolExecutionRequest);
            conversationAccess.logToolExecutionResultMessage(getConversationCoordinate(), toolExecutionResultMessage);;
            chatMemory.add(toolExecutionResultMessage);
            count++;
        }
    }

    private ToolExecutionResultMessage executeTool(ToolExecutionRequest toolExecutionRequest) {
        ToolExecutor toolExecutor = toolService.getToolExecutor(toolExecutionRequest.name());
        String toolExecutionResult = toolExecutor.execute(toolExecutionRequest);
        return ToolExecutionResultMessage.toolExecutionResultMessage(toolExecutionRequest.name(), toolExecutionResult);
    }

}
