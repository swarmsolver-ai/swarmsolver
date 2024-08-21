package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.File;
import java.util.Optional;

public class FileLocationTool extends AbstractFileTool  {

    public FileLocationTool(FileToolSettings fileToolSettings) {
        super(fileToolSettings);
        if (fileToolSettings == null) throw new IllegalArgumentException("error in FileLocationTool. fileToolSettings is required");
        if (fileToolSettings.getBaseDir() == null) throw new IllegalArgumentException("error in FileLocationTool. baseDir is required");
    }

    @Tool("find the full path to a file by file name, e.g. file.txt will return /path/to/file.txt. if not found will return NOT FOUND")
    public String find(@P("the name of the file e.g. file.txt") String fileName) {
        File baseDirectory = new File(fileToolSettings.getBaseDir());
        return recursiveSearchFile(baseDirectory, fileName)
                .orElse(fileName + " NOT FOUND");
    }

    private Optional<String> recursiveSearchFile(File directory, String fileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    Optional<String> result = recursiveSearchFile(file, fileName);
                    if (result.isPresent()) {
                        return result;
                    }
                } else if (file.getName().equals(fileName)) {
                    // File found
                    return Optional.of(getRelativePath(fileToolSettings.getBaseDir(), file.getAbsolutePath()));
                }
            }
        }

        return Optional.empty();
    }

    private String getRelativePath(String baseDir, String absolutePath) {
        String relativePath = absolutePath.substring(baseDir.length());
        return relativePath.startsWith(File.separator) ? relativePath.substring(1) : relativePath;
    }


}
