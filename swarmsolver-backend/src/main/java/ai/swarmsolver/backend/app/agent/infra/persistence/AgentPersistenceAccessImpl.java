package ai.swarmsolver.backend.app.agent.infra.persistence;

import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import ai.swarmsolver.backend.app.agent.domain.AgentState;
import ai.swarmsolver.backend.app.agent.domain.AgentPersistenceAccess;
import ai.swarmsolver.backend.app.agent.domain.AgentRepository;

public class AgentPersistenceAccessImpl implements AgentPersistenceAccess {

    private final AgentCoordinate agentCoordinate;

    private final AgentRepository agentRepository;

    public AgentPersistenceAccessImpl(AgentCoordinate agentCoordinate, AgentRepository agentRepository) {
        this.agentCoordinate = agentCoordinate;
        this.agentRepository = agentRepository;
    }


    @Override
    public void persist(AgentState memento) {
        agentRepository.writeState(agentCoordinate, memento);
    }

}
