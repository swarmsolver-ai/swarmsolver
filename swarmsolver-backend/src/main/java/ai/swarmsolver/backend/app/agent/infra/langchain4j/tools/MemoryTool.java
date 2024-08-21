package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class MemoryTool {
    private static String memory;

    @Tool("Stores the given text in memory")
    public void writeToMemory(@P("The text to be stored in memory") String text) {
        memory = text;
    }

    @Tool("Retrieves the text stored in memory")
    public String readFromMemory() {
        if (memory == null || memory.isBlank()) return "MEMORY EMPTY";
        return memory;
    }
}
