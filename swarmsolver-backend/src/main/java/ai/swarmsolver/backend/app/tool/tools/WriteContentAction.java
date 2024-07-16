package ai.swarmsolver.backend.app.tool.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class WriteContentAction {

    public String writeContentToFile(String baseDir, String content, String filePath) {
        Path fullPath = Paths.get(baseDir, filePath);
        if (!isSubPath(new File(baseDir).toPath(), fullPath)) return String.format("UNSUCCESSFUL: %s is not a valid file path", filePath);

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
