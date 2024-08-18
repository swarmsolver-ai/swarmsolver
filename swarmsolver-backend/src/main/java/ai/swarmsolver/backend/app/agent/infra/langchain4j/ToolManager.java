package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolManager {

    private final Map<String, Object> toolBeanByFunctionName = new HashMap<>();

    @Getter
    private final List<ToolSpecification> toolSpecifications = new ArrayList<>();

    public ToolManager(List<Object> toolBeans) {
        if (toolBeans == null) return;
        for (Object toolBean : toolBeans) {
            List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(toolBean);
            for (ToolSpecification toolSpecification : toolSpecifications) {
                toolBeanByFunctionName.put(toolSpecification.name(), toolBean);
            }
            this.toolSpecifications.addAll(toolSpecifications);
        }
    }

    public String execute(ToolExecutionRequest toolExecutionRequest) {
        Object toolBean = toolBeanByFunctionName.get(toolExecutionRequest.name());
        ToolExecutor toolExecutor = new DefaultToolExecutor(toolBean, toolExecutionRequest);
        return toolExecutor.execute(toolExecutionRequest, toolBean);
    }

    public boolean isAsyncRequest(ToolExecutionRequest toolExecutionRequest) {
        String methodName = toolExecutionRequest.name();
        Object toolBean = toolBeanByFunctionName.get(methodName);
        Method[] methods = toolBean.getClass().getDeclaredMethods();

        // Filter the methods to find the one with the correct name
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                if (method.isAnnotationPresent(AsyncTool.class)) {
                    return true;
                }
            }
        }
        return false;
    }
}
