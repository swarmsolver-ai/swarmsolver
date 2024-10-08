package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.ToolWithWorkspaceAccess;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.WorkspaceAccess;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;

import java.io.IOException;
import java.util.List;


@Builder
@AllArgsConstructor
public class ResourcesTool implements ToolWithWorkspaceAccess {

    @Builder.Default
    private WorkspaceAccess workspaceAccess = null;

    private ResourceScope scope;

    @Singular
    private List<ResourceDescriptor> resources;

    @Override
    public void setWorkspaceAccess(WorkspaceAccess workspaceAccess) {
        this.workspaceAccess = workspaceAccess;
    }

    @Tool("Read the contents of a resource in the workspace")
    public String readResource(@P("name of the resource") String resourceName) {
        try {
            return switch(scope) {
                case WORKSPACE -> workspaceAccess.readWorkspaceResource(resourceName);
                case TASK -> workspaceAccess.readTaskResource(resourceName);
                case STEP -> workspaceAccess.readStepResource(resourceName);
            };
        } catch (Exception e) {
            return "RESOURCE NOT FOUND";
        }
    }

    @Tool("Write content to a resource in the workspace")
    public void writeResource(@P("name of the resource") String resourceName, @P("content to write") String content) {
        try {
            switch(scope) {
                case WORKSPACE -> workspaceAccess.writeWorkspaceResource(resourceName, content);
                case TASK -> workspaceAccess.writeTaskResource(resourceName, content);
                case STEP -> workspaceAccess.writeStepResource(resourceName, content);
            };
        } catch (IOException e) {
            throw new RuntimeException("Error writing resource: " + resourceName, e);
        }
    }

    @Tool("List all resources in the workspace")
    public String list() {
        if (resources == null || resources.isEmpty()) {
            return "NO RESOURCES";
        }

        StringBuilder sb = new StringBuilder();
        for (ResourceDescriptor resource : resources) {
            sb.append(resource.getName())
                    .append(": ")
                    .append(resource.getDescription())
                    .append("\n");
        }

        return sb.toString();
    }


}
