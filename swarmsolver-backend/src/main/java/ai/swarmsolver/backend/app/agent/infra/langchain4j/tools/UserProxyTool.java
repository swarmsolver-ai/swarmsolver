package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import ai.swarmsolver.backend.app.agent.infra.langchain4j.AsyncTool;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;


public class UserProxyTool {

    @Tool("Ask question to the user")
    @AsyncTool
    public String userProxy_askQuestion(@P("the question") String question) {
        return "";
    }


}
