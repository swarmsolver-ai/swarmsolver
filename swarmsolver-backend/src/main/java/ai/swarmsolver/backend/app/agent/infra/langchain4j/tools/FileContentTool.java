package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContentTool extends AbstractFileTool {

    public FileContentTool(FileToolSettings fileToolSettings) {
        super(fileToolSettings);
        if (fileToolSettings == null) throw new IllegalArgumentException("error in FileContentTool. fileToolSettings is required");
        if (fileToolSettings.getBaseDir() == null) throw new IllegalArgumentException("error in FileContentTool. baseDir is required");
    }

    @Tool("Read the contents of a file")
    public String readFileContent(@P("path of the file, e.g. /path/to/file.txt ") String fileLocation) {
        Path filePath = Paths.get(fileToolSettings.getBaseDir(), fileLocation);

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            return new String(fileBytes);
        } catch (IOException e) {
            return "FILE NOT FOUND";
        }
    }

}
