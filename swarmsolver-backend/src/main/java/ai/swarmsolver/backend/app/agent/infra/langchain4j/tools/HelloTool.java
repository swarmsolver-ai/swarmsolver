package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;

public class HelloTool  {

    @Tool("hello tool")
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
