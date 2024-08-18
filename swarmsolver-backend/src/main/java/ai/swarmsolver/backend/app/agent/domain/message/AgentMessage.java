package ai.swarmsolver.backend.app.agent.domain.message;

import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class AgentMessage {
    private final Address from;
    private final Address to;
    private final String text;

    public static AgentMessage ofUserToAgent(AgentCoordinate to, String message) {
        return AgentMessage.builder()
                .from(Address.builder()
                        .type(AddressType.USER)
                        .build())
                .to(Address.builder()
                        .type(AddressType.AGENT)
                        .agentCoordinate(to)
                        .build())
                .text(message)
                .build();
    }

    public static AgentMessage ofAgentToUser(AgentCoordinate from, String message) {
        return AgentMessage.builder()
                .from(Address.builder()
                        .type(AddressType.AGENT)
                        .agentCoordinate(from)
                        .build())
                .to(Address.builder()
                        .type(AddressType.USER)
                        .build())
                .text(message)
                .build();
    }

    public static AgentMessage ofAgentToAgent(AgentCoordinate from, AgentCoordinate to, String message) {
        return AgentMessage.builder()
                .from(Address.builder()
                        .type(AddressType.AGENT)
                        .agentCoordinate(from)
                        .build())
                .to(Address.builder()
                        .type(AddressType.AGENT)
                        .agentCoordinate(to)
                        .build())
                .text(message)
                .build();
    }
}
