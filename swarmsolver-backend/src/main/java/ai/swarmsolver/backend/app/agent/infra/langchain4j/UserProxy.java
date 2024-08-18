package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;


public class UserProxy {

    @Tool("Ask question to the user")
    @AsyncTool
    public String userProxy_askQuestion(@P("the question") String question) {
        return "";
    }


}
