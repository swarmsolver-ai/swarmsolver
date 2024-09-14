package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CommandTool {

    private final CommandToolSettings settings;

    public CommandTool(CommandToolSettings settings) {
        this.settings = settings;
    }

    @Tool("Runs a command on the underlying operating system and returns the output. See the list of allowed commands below.")
    String runCommand(@P("command with arguments") String command
    ) {
        CommandTemplate matchingTemplate = getMatchingTemplate(command);
        if (matchingTemplate == null) {
            return "Command not allowed: " + command;
        }

        StringBuilder output = new StringBuilder();
        StringBuilder error = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-i", "-c", command);
            processBuilder.directory(new File(settings.getInitialDir()));
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                error.append("Command exited with code: ").append(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            error.append("Error running command: ").append(e.getMessage());
        }

        if (error.length() > 0) {
            return error.toString().trim();
        } else {
            return output.toString().trim();
        }
    }

    @Tool("Here is a list of possible commands.")
    String listCommands() {
        return settings.getCommandTemplates().stream()
                .map(t -> "Pattern: " + t.getPattern().pattern() + "\n"
                        + "Description: " + t.getDescription() + "\n"
                        + "Example: " + t.getExample())
                .collect(Collectors.joining("\n\n"));
    }

    private CommandTemplate getMatchingTemplate(String command) {
        for (CommandTemplate template : settings.getCommandTemplates()) {
            if (template.getPattern().matcher(command).matches()) {
                return template;
            }
        }
        return null;
    }
}
