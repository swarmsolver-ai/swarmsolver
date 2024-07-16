package ai.swarmsolver.backend.app.testinfra;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class DirectoryStructurePrinter {

    public static void main(String[] args) {
        // Specify the root directory path here
        String rootPath = "path_to_your_root_folder";
        File rootDir = new File(rootPath);

        if (rootDir.exists() && rootDir.isDirectory()) {
            log.info("Directory structure of: " + rootPath);
            printDirectory(rootDir, 0);
        } else {
            log.error("The provided path is not a valid directory.");
        }
    }

    public static void dumpWorkspaceState(File dir, String stateDescription) {
        log.info(stateDescription);
        log.info("=".repeat(stateDescription.length()));
        printDirectory(dir, 0);
        log.info("");
    }

    private static void printDirectory(File dir, int level) {
        if (dir == null || !dir.exists()) {
            return;
        }

        String indent = " ".repeat(level * 4);
        log.info(indent + "d " + dir.getName());

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    log.info(indent + " ".repeat(4) + "- " + file.getName());
                }
            }
        }
    }
}
