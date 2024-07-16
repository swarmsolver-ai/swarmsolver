package ai.swarmsolver.backend.app.testinfra;

import dev.langchain4j.data.message.UserMessage;
import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.task.repository.TaskWorkspace;
import lombok.Builder;
import lombok.Getter;

@Builder
public class MockAgent implements Agent {

     private final AgentConversationAccess conversationAccess;

    private final AgentPersistenceAccess persistenceAccess;

    private final AgentState agentState;

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
        conversationAccess.logUserMessage(getConversationCoordinate(), userMessage);
    }
    private void initIfNeeded() {
        if (!agentState.isInitialized()) {
            agentState.setInitialized(true);
            persistenceAccess.persist(agentState);
        }
    }

}
