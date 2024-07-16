package ai.swarmsolver.backend.app.agent.domain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import org.springframework.scheduling.annotation.Async;

public interface AgentConversationAccess {

    ConversationCoordinate initConversation(TaskCoordinate taskCoordinate);

    void logAiMessageResponse(ConversationCoordinate conversationCoordinate, Response<AiMessage> response);

    @Async
    void logUserMessage(ConversationCoordinate conversationCoordinate, UserMessage userMessage);

    void logToolExecutionResultMessage(ConversationCoordinate conversationCoordinate, ToolExecutionResultMessage toolExecutionResultMessage);
}
