package ai.swarmsolver.backend.app.agent.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentCoordinate {
    private TaskCoordinate taskCoordinate;
    private AgentId agentId;

    private AgentCoordinate() {
    }

    static public AgentCoordinate of(TaskCoordinate taskCoordinate, AgentId agentId) {
        return new AgentCoordinate(taskCoordinate, agentId);
    }
}
