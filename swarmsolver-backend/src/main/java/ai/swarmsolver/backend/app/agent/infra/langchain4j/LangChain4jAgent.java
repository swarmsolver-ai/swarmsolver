package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import ai.swarmsolver.backend.app.agent.domain.message.AgentMessage;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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

    private State state = State.CREATED;

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
    public void handleMessage(AgentMessage message) {

        do {
            state = switch (state) {
                case CREATED -> initialize();
                case READY_TO_ACCEPT_MESSAGES -> handleInitialMessage(message);
                case CALLING_CHAT_MODEL -> callChatModel();
                case DONE_PROCESSING_CHAT_MODEL -> State.READY_TO_ACCEPT_MESSAGES;
                case PROCESSING_TOOL_REQUESTS -> handleToolExecutionRequests();
                case WAITING_CALLBACK -> handleCallBackMessage(message);
                case DONE_EXECUTING_TOOL_REQUESTS -> State.CALLING_CHAT_MODEL;
            };
        } while(!returnState(state));

    }

    private boolean returnState(State state) {
        return state.equals(State.WAITING_CALLBACK) || state.equals(State.DONE_PROCESSING_CHAT_MODEL);
    }

    private ToolManager toolManager;

    private State initialize() {
        if (tools != null) {
            toolManager = new ToolManager(tools);
        }
        addSystemMessage();
        return State.READY_TO_ACCEPT_MESSAGES;
    }

    private void addSystemMessage() {
        log.info("system message in agent: " + systemMessage);
        if (systemMessage != null) {
            chatMemory.add(new SystemMessage(systemMessage));
        }
    }

    private State handleInitialMessage(AgentMessage message) {
        UserMessage userMessage = new UserMessage(message.getText());
        chatMemory.add(userMessage);
        conversationAccess.logUserMessage(getConversationCoordinate(), userMessage);
        return State.CALLING_CHAT_MODEL;
    }


    List<ToolExecutionRequest> toolExecutionRequests;
    ToolExecutionRequest toolExecutionRequestWaitingForCallback;

    int count = 0;

    private State callChatModel() {
        Response<AiMessage> response = chatLanguageModel.generate(chatMemory.messages(), toolManager != null ? toolManager.getToolSpecifications() : null);
        chatMemory.add(response.content());
        conversationAccess.logAiMessageResponse(getConversationCoordinate(), response);

        toolExecutionRequests = (response.content()).toolExecutionRequests();
        if (toolExecutionRequests == null) {
            return State.DONE_PROCESSING_CHAT_MODEL;
        } else {
            toolExecutionRequests = new ArrayList<>(toolExecutionRequests);
            return State.PROCESSING_TOOL_REQUESTS;
        }
    }

    private State handleToolExecutionRequests() {
        if (toolExecutionRequests != null && !toolExecutionRequests.isEmpty()) {
            ToolExecutionRequest toolExecutionRequest = toolExecutionRequests.get(0);
            if (toolManager.isAsyncRequest(toolExecutionRequest)) {
                return startToolAsync(toolExecutionRequest);

            } else {
                return executeToolSync(toolExecutionRequest);
            }
        } else {
            return State.DONE_EXECUTING_TOOL_REQUESTS;
        }
    }

    private State  executeToolSync(ToolExecutionRequest toolExecutionRequest) {
        String toolExecutionResult = toolManager.execute(toolExecutionRequest);
        toolExecutionRequests.remove(toolExecutionRequest);
        ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.toolExecutionResultMessage(toolExecutionRequest, toolExecutionResult);
        conversationAccess.logToolExecutionResultMessage(getConversationCoordinate(), toolExecutionResultMessage);
        chatMemory.add(toolExecutionResultMessage);
        return State.PROCESSING_TOOL_REQUESTS;
    }

    private State startToolAsync(ToolExecutionRequest toolExecutionRequest) {
        toolManager.execute(toolExecutionRequest);
        toolExecutionRequestWaitingForCallback = toolExecutionRequest;
        return State.WAITING_CALLBACK;
    }

    private State handleCallBackMessage(AgentMessage callBackMessage) {
        String toolExecutionResult = callBackMessage.getText();
        ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.toolExecutionResultMessage(toolExecutionRequestWaitingForCallback, toolExecutionResult);
        conversationAccess.logToolExecutionResultMessage(getConversationCoordinate(), toolExecutionResultMessage);
        chatMemory.add(toolExecutionResultMessage);

        toolExecutionRequests.remove(toolExecutionRequestWaitingForCallback);
        return State.PROCESSING_TOOL_REQUESTS;
    }

}
