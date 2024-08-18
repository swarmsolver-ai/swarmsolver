package ai.swarmsolver.backend.app.agent.domain.message;

import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private AddressType type;
    private AgentCoordinate agentCoordinate;
}
