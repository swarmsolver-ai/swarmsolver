package ai.swarmsolver.backend.app.agent.app;

import ai.swarmsolver.backend.app.agent.domain.AgentId;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.conversation.ConversationId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AgentSummaryDTO {
    private AgentId agentId;
    private ConversationCoordinate conversationCoordinate;
}
