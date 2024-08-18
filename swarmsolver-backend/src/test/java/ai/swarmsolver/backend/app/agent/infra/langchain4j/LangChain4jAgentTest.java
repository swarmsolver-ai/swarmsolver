package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.agent.domain.message.AgentMessage;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LangChain4jAgentTest {


    private ChatLanguageModel chatLanguageModel;
    private ChatMemory chatMemory;
    private AgentConversationAccess conversationAccess;
    private AgentPersistenceAccess persistenceAccess;

    @BeforeEach
    void setUp() {
        chatLanguageModel = Mockito.mock(ChatLanguageModel.class);
        chatMemory = Mockito.mock(ChatMemory.class);
        conversationAccess = Mockito.mock(AgentConversationAccess.class);
        persistenceAccess = Mockito.mock(AgentPersistenceAccess.class);

    }

    @Test
    void handleMessage_sequence_using_user_proxy() {

        /// Scenario:
        ///  - agent has a user proxy tool
        ///  - user asks a question triggering the Agent to use the user proxy tool
        ///  - user answers the question of the Agent
        ///  - LLM proceeds using user's answer

        // given an agent with a user proxy tool
        TaskId mainTaskId = TaskId.of(UUID.randomUUID().toString());
        TaskCoordinate taskCoordinate = TaskCoordinate.of("workspace", mainTaskId);

        AgentId agentId = AgentId.of(UUID.randomUUID().toString());
        AgentCoordinate agentCoordinate = AgentCoordinate.of(taskCoordinate, agentId);

        LangChain4jAgent agent = LangChain4jAgent.builder()
                .languageModel(chatLanguageModel)
                .chatMemory(chatMemory)
                .tools(List.of(new UserProxy()))
                .systemMessage("System message")
                .agentState(new AgentState())
                .conversationAccess(conversationAccess)
                .persistenceAccess(persistenceAccess)
                .build();


        /// STEP 1 : user sends initial message (what's my name)

        // given an initial message from user to the agent
        String userMessage = "Ask me my name. ";
        AgentMessage  message = AgentMessage.ofUserToAgent(agentCoordinate, userMessage);

        // given the LLM answers with a tool execution request to the user proxy
        ToolExecutionRequest toolExecutionRequest = ToolExecutionRequest.builder()
                .name("userProxy_askQuestion") // method name of UserProxy class
                .arguments("{ \"arg0\" : \"what is your name\"}") // arguments as json map
                .build();
        AiMessage aiMessage = new AiMessage("response", Collections.singletonList(toolExecutionRequest));
        Response<AiMessage> response = new Response<>(aiMessage);
        when(chatLanguageModel.generate(Mockito.any(List.class), Mockito.any(List.class))).thenReturn(response);

        // when the message is handled by the agent
        agent.handleMessage(message);

        // then the agent is in WAITING_CALLBACK state
        verify(chatMemory).add(new UserMessage(userMessage));
        verify(conversationAccess).logUserMessage(agent.getConversationCoordinate(), new UserMessage(userMessage));
        assertEquals(State.WAITING_CALLBACK, getState(agent));

        /// STEP 2: the user answers

        // given the callback from the user
        String userMessage2 = "My name is Bram.";
        AgentMessage  message2 = AgentMessage.ofUserToAgent(agentCoordinate, userMessage2);

        // given the Agent now answers, using the info given by the user
        AiMessage aiMessage2 = new AiMessage("Hi Bram");
        Response<AiMessage> response2 = new Response<>(aiMessage2);
        when(chatLanguageModel.generate(Mockito.any(List.class), Mockito.any(List.class))).thenReturn(response2);

        // when the callback message is handled by the agent
        agent.handleMessage(message2);

        // then the agent is in DONE_PROCESSING_CHAT_MODEL state
        //verify(chatMemory).add(new UserMessage(userMessage2));
        //verify(conversationAccess).logUserMessage(agent.getConversationCoordinate(), new UserMessage(userMessage2));
        assertEquals(State.DONE_PROCESSING_CHAT_MODEL, getState(agent));


    }

    @SneakyThrows
    State getState(Agent agent) {
        Field field = agent.getClass().getDeclaredField("state");
        field.setAccessible(true);
        return (State) field.get(agent);
    }

}