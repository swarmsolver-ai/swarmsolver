package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.ToolWithWorkspaceAccess;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.WorkspaceAccess;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.IOException;

public class ResourceTool implements ToolWithWorkspaceAccess {

    private WorkspaceAccess workspaceAccess;

    @Override
    public void setWorkspaceAccess(WorkspaceAccess workspaceAccess) {
        this.workspaceAccess = workspaceAccess;
    }

    @Tool("Read the contents of a resource in the workspace")
    public String readResource(@P("name of the resource") String resourceName) {
        try {
            return workspaceAccess.readResource(resourceName);
        } catch (Exception e) {
            return "RESOURCE NOT FOUND";
        }
    }

    @Tool("Write content to a resource in the workspace")
    public void writeResource(@P("name of the resource") String resourceName, @P("content to write") String content) {
        try {
            workspaceAccess.writeResource(resourceName, content);
        } catch (IOException e) {
            throw new RuntimeException("Error writing resource: " + resourceName, e);
        }
    }
}
