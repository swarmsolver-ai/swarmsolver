package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.app.task.repository.TaskWorkspace;
import ai.swarmsolver.backend.infra.DirectoryStructure;

import java.io.File;

public class AgentWorkSpace {

    private final TaskWorkspace taskWorkspace;

    private final AgentId agentId;

    public AgentWorkSpace(TaskWorkspace taskWorkspace, AgentId agentId) {
        this.taskWorkspace = taskWorkspace;
        this.agentId = agentId;
    }

    public static AgentWorkSpace of(DirectoryStructure directoryStructure, AgentCoordinate agentCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, agentCoordinate.getTaskCoordinate());
        return new AgentWorkSpace(taskWorkspace, agentCoordinate.getAgentId());

    }


    public File getAgentStateFile() {
        return new File(taskWorkspace.getSubTaskDir(), String.format("state-%s.json", agentId.getIdentifier()));
    }

    public File getAgentChatHistoryFile() {
        return new File(taskWorkspace.getSubTaskDir(), String.format("chat-history-%s.json", agentId.getIdentifier()));
    }


    public File getResourceFile(String resourceName) {
        return new File(taskWorkspace.getSubTaskDir(), String.format("resource-%s.txt", resourceName));
    }
}
