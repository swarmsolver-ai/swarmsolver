package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

public class AbstractFileTool {

    protected FileToolSettings fileToolSettings;

    public AbstractFileTool(FileToolSettings fileToolSettings) {
        this.fileToolSettings = fileToolSettings;
    }
}
