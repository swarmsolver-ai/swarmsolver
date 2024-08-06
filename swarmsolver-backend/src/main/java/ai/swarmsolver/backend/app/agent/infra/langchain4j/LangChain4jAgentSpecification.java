package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import ai.swarmsolver.backend.app.agent.domain.AgentSpecification;
import dev.langchain4j.agent.tool.ToolSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LangChain4jAgentSpecification implements AgentSpecification<LangChain4jAgent> {

    @Override
    public Class<LangChain4jAgent> agentClass() {
        return LangChain4jAgent.class;
    }

    private ChatModelSupplier chatModelSupplier;

    private String systemMessage;

    private List<ToolSpecification> toolSpecifications;
}
