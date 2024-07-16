package ai.swarmsolver.backend.app.tool.service;

import dev.langchain4j.agent.tool.ToolSpecification;
import ai.swarmsolver.backend.app.tool.model.ToolDescription;
import ai.swarmsolver.backend.app.tool.model.ToolExecutor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolInfo {

    private ToolSpecification toolSpecification;
    private ToolDescription toolDescription;
    private ToolExecutor toolExecutor;
}
