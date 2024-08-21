package ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace;

import ai.swarmsolver.backend.app.agent.domain.AgentWorkSpace;

public interface ToolWithWorkspaceAccess {
    void setWorkspaceAccess(WorkspaceAccess workspaceAccess);
}
