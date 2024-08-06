package ai.swarmsolver.backend.app.tool.tools;

import dev.langchain4j.agent.tool.Tool;

public class HelloTool  {

    @Tool("hello tool")
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
