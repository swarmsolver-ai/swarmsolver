package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AgentState {
    private boolean initialized;
    private String agentName;
    private String systemMessage;
    private ConversationCoordinate conversationCoordinate;
    private AgentCoordinate agentCoordinate;
}
