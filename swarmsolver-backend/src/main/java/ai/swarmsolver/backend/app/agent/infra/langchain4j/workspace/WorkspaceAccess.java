package ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace;

import ai.swarmsolver.backend.app.agent.domain.AgentWorkSpace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WorkspaceAccess {

    private final AgentWorkSpace agentWorkSpace;

    public WorkspaceAccess(AgentWorkSpace agentWorkSpace) {
        this.agentWorkSpace = agentWorkSpace;
    }

    public String readResource(String resourceName) throws IOException {
        File resourceFile = getResourceFile(resourceName);
        if (resourceFile.exists()) {
            return new String(Files.readAllBytes(Paths.get(resourceFile.getAbsolutePath())));
        } else {
            throw new IOException("RESOURCE NOT FOUND");
        }
    }

    public void writeResource(String resourceName, String content) throws IOException {
        File resourceFile = getResourceFile(resourceName);
        Files.write(Paths.get(resourceFile.getAbsolutePath()), content.getBytes());
    }

    private File getResourceFile(String resourceName) {
        return agentWorkSpace.getResourceFile(resourceName);
    }

}
