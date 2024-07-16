package ai.swarmsolver.backend.app.tool.scripting;

import ai.swarmsolver.backend.infra.scripting.ScriptBeanFactory;
import ai.swarmsolver.backend.app.tool.service.ToolService;
import org.springframework.stereotype.Component;

@Component
public class ToolsBeanFactory implements ScriptBeanFactory<ToolsBean> {

    private final ToolService toolService;

    public ToolsBeanFactory(ToolService toolService) {
        this.toolService = toolService;
    }

    public ToolsBean createInstance() {
        return new ToolsBean(toolService);
    }

    @Override
    public String beanName() {
        return "tools";
    }
}
