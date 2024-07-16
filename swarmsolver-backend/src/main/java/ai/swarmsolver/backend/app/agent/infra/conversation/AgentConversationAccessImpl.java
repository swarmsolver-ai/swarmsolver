package ai.swarmsolver.backend.app.agent.infra.conversation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import ai.swarmsolver.backend.app.agent.domain.AgentConversationAccess;
import ai.swarmsolver.backend.app.conversation.*;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AgentConversationAccessImpl implements AgentConversationAccess {

    private final ConversationService conversationService;

    private ObjectMapper objectMapper;

    public AgentConversationAccessImpl(ConversationService conversationService) {
        this.conversationService = conversationService;
        initMapper();
    }

    private void initMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.objectMapper = objectMapper;
    }


    @Override
    public ConversationCoordinate initConversation(TaskCoordinate taskCoordinate) {
        return conversationService.initConversation(taskCoordinate);
    }

    @Override
    public void logAiMessageResponse(ConversationCoordinate conversationCoordinate, Response<AiMessage> response) {
        try {
            this.conversationService.add(conversationCoordinate, Message.builder()
                    .from("llm")
                    .to("agent")
                    .timeStamp(LocalDateTime.now().toString())
                    .messageType(MessageType.AGENT_MESSAGE)
                    .messageBody(objectMapper.writeValueAsString(response))
                    .build());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logUserMessage(ConversationCoordinate conversationCoordinate, UserMessage userMessage) {
        try {
            this.conversationService.add(conversationCoordinate,
                    Message.builder()
                            .from("user")
                            .to("agent")
                            .timeStamp(LocalDateTime.now().toString())
                            .messageType(MessageType.USER_MESSAGE)
                            .messageBody(objectMapper.writeValueAsString(userMessage))
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void logToolExecutionResultMessage(ConversationCoordinate conversationCoordinate, ToolExecutionResultMessage message) {
        try {
            this.conversationService.add(conversationCoordinate,
                    Message.builder()
                            .from("tool")
                            .to("agent")
                            .timeStamp(LocalDateTime.now().toString())
                            .messageType(MessageType.TOOL_EXECUTION_MESSAGE)
                            .messageBody(objectMapper.writeValueAsString(message))
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
