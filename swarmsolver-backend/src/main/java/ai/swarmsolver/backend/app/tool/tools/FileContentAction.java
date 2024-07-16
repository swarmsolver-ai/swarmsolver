package ai.swarmsolver.backend.app.tool.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContentAction {
    private String baseDir;

    public FileContentAction(String baseDir) {
        this.baseDir = baseDir;
    }

    public String readFileContent(String fileLocation) {
        Path filePath = Paths.get(baseDir, fileLocation);

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            return new String(fileBytes);
        } catch (IOException e) {
            return "FILE NOT FOUND";
        }
    }

}
