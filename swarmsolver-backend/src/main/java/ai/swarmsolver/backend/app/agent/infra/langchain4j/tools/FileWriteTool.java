package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class FileWriteTool extends AbstractFileTool{

    public FileWriteTool(FileToolSettings fileToolSettings) {
        super(fileToolSettings);
        if (fileToolSettings == null) throw new IllegalArgumentException("error in FileContentTool. fileToolSettings is required");
        if (fileToolSettings.getBaseDir() == null) throw new IllegalArgumentException("error in FileContentTool. baseDir is required");
    }

    @Tool("Write to a file")
    public String writeContentToFile(@P("file content") String content, @P("path of the file, e.g. /path/to/file.txt ")String filePath) {
        Path fullPath = Paths.get(fileToolSettings.getBaseDir(), filePath);
        if (!isSubPath(new File(fileToolSettings.getBaseDir()).toPath(), fullPath)) return String.format("UNSUCCESSFUL: %s is not a valid file path", filePath);

        try {
            Files.createDirectories(fullPath.getParent());
        } catch (IOException e) {
            return "UNSUCCESSFUL: Error creating directories : " + e.getMessage();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath.toString()))) {
            writer.write(content);
            return ("SUCCESS. File written successfully");
        } catch (IOException e) {
            return "UNSUCCESSFUL: Error writing to file: " + e.getMessage();
        }
    }

    private boolean isSubPath(Path basePath, Path givenPath) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + basePath + "/**");
        return pathMatcher.matches(givenPath);
    }


}
