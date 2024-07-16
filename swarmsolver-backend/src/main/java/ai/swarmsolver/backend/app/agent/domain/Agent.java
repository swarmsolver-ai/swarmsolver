package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.conversation.ConversationId;

public interface Agent {
    AgentCoordinate getAgentCoordinate();
    ConversationCoordinate getConversationCoordinate();
    void handleMessage(String message);

}
