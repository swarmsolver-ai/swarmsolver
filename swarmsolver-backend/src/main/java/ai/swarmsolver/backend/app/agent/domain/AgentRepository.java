package ai.swarmsolver.backend.app.agent.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AgentRepository {

    private final DirectoryStructure directoryStructure;

    private final Map<AgentId, Agent> agents = new HashMap<>();

    private final ObjectMapper objectMapper;

    public AgentRepository(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        this.objectMapper = new ObjectMapper();
    }

    public void saveToCache(Agent agent) {
        agents.put(agent.getAgentCoordinate().getAgentId(), agent);
    }

    @SneakyThrows
    public void writeState(AgentCoordinate agentCoordinate, AgentState agentState){
        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, agentCoordinate);
        objectMapper.writeValue(agentWorkSpace.getAgentStateFile(), agentState);
    }

    @SneakyThrows
    public AgentState readState(AgentCoordinate agentCoordinate) {
        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, agentCoordinate);
        return objectMapper.readValue(agentWorkSpace.getAgentStateFile(), AgentState.class);
    }

    public Agent loadFromCache(AgentCoordinate agentCoordinate) {
        return agents.get(agentCoordinate.getAgentId());
    }

    public void resetCache() {
        agents.clear();
    }
}
