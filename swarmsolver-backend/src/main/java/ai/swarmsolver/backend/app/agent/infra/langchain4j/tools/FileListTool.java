package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListTool extends AbstractFileTool {

    public FileListTool(FileToolSettings fileToolSettings) {
        super(fileToolSettings);
        if (fileToolSettings == null) throw new IllegalArgumentException("error in FileListTool. fileToolSettings is required");
        if (fileToolSettings.getBaseDir() == null) throw new IllegalArgumentException("error in FileListTool. baseDir is required");
    }

    @Tool("get a list of all files under the base directory")
    public List<String> listFiles() {
        return listFilesUnderDirectory("");
    }

    @Tool("get a list of all files under the specified subdirectory")
    public List<String> listFilesUnderDirectory(@P("the subdirectory to search within") String subDir) {
        List<String> files = new ArrayList<>();
        File baseDirectory = new File(fileToolSettings.getBaseDir());
        File subDirectory = new File(baseDirectory, subDir);
        recursiveListFiles(subDirectory, files);
        return files;
    }

    private void recursiveListFiles(File directory, List<String> files) {
        File[] directoryFiles = directory.listFiles();
        if (directoryFiles != null) {
            for (File file : directoryFiles) {
                if (file.isDirectory()) {
                    recursiveListFiles(file, files);
                } else {
                    files.add(getRelativePath(fileToolSettings.getBaseDir(), file.getAbsolutePath()));
                }
            }
        }
    }

    private String getRelativePath(String baseDir, String absolutePath) {
        String relativePath = absolutePath.substring(baseDir.length());
        return relativePath.startsWith(File.separator) ? relativePath.substring(1) : relativePath;
    }
}
