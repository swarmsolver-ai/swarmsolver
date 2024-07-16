package ai.swarmsolver.backend.app.tool.tools;

import java.io.File;
import java.util.Optional;

public class FileLocationAction {
    private final String baseDir;

    public FileLocationAction(String baseDir) {
        this.baseDir = baseDir;
    }

    public String find(String fileName) {
        File baseDirectory = new File(baseDir);
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
                    return Optional.of(getRelativePath(baseDir, file.getAbsolutePath()));
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
