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

    private File getWorkspaceResourceFile(String resourceName) {
        return agentWorkSpace.getWorkspaceResourceFile(resourceName);
    }

    private File getTaskResourceFile(String resourceName) {
        return agentWorkSpace.getTaskResourceFile(resourceName);
    }

    private File getStepResourceFile(String resourceName) {
        return agentWorkSpace.getStepResourceFile(resourceName);
    }

    public String readWorkspaceResource(String resourceName) throws IOException{
        File resourceFile = getWorkspaceResourceFile(resourceName);
        if (resourceFile.exists()) {
            return new String(Files.readAllBytes(Paths.get(resourceFile.getAbsolutePath())));
        } else {
            throw new IOException("RESOURCE NOT FOUND");
        }
    }

    public String readTaskResource(String resourceName) throws IOException{
        File resourceFile = getTaskResourceFile(resourceName);
        if (resourceFile.exists()) {
            return new String(Files.readAllBytes(Paths.get(resourceFile.getAbsolutePath())));
        } else {
            throw new IOException("RESOURCE NOT FOUND");
        }
    }

    public String readStepResource(String resourceName) throws IOException {
        File resourceFile = getStepResourceFile(resourceName);
        if (resourceFile.exists()) {
            return new String(Files.readAllBytes(Paths.get(resourceFile.getAbsolutePath())));
        } else {
            throw new IOException("RESOURCE NOT FOUND");
        }
    }

    public void writeWorkspaceResource(String resourceName, String content) throws IOException {
        File resourceFile = getWorkspaceResourceFile(resourceName);
        resourceFile.mkdirs();
        Files.write(Paths.get(resourceFile.getAbsolutePath()), content.getBytes());
    }

    public void writeTaskResource(String resourceName, String content) throws IOException {
        File resourceFile = getTaskResourceFile(resourceName);
        Files.write(Paths.get(resourceFile.getAbsolutePath()), content.getBytes());
    }

    public void writeStepResource(String resourceName, String content) throws IOException {
        File resourceFile = getStepResourceFile(resourceName);
        Files.write(Paths.get(resourceFile.getAbsolutePath()), content.getBytes());
    }
}
