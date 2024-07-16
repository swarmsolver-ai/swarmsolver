package ai.swarmsolver.backend.app.agent.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class AgentId {

    private String identifier;

    public static AgentId of(String identifier) {
        return new AgentId(identifier);
    }
}
