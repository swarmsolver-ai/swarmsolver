package ai.swarmsolver.backend.app.tool.scripting;

import ai.swarmsolver.backend.app.tool.service.ToolInfo;
import ai.swarmsolver.backend.app.tool.service.ToolService;

import java.util.List;

public class ToolsBean {

    private final ToolService toolService;


    public ToolsBean(ToolService toolService) {
        this.toolService = toolService;
    }

    public List<ToolInfo> getTools() {
        return toolService.getToolInfos();
    }

}
