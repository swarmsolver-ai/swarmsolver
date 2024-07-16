package ai.swarmsolver.backend.app.tool.service;

import dev.langchain4j.agent.tool.JsonSchemaProperty;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.internal.Utils;
import ai.swarmsolver.backend.app.tool.annotation.Tool;
import ai.swarmsolver.backend.app.tool.model.ToolExecutor;
import ai.swarmsolver.backend.app.tool.model.ToolParameter;
import ai.swarmsolver.backend.app.tool.model.ToolDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ToolService {

    private final Map<String, ToolInfo> toolInfoByName = new HashMap<>();

    public ToolService(List<ToolBean> toolBeans) {
        for (ToolBean toolBean: toolBeans) {
            register(toolBean);
        }
    }

    public void register(ToolInfo toolInfo) {
        String name = toolInfo.getToolDescription().getName();
        log.debug(String.format("registering tool %s", name));
        toolInfoByName.put(name, toolInfo);
    }



    public void register(Object objectWithTool) {
        Method[] methods = objectWithTool.getClass().getDeclaredMethods();
        for(int idx = 0; idx < methods.length; ++idx) {
            Method method = methods[idx];
            if (method.isAnnotationPresent(Tool.class)) {
                register(ToolInfo.builder()
                        .toolDescription(toolDescriptionFrom(method))
                        .toolExecutor(new ToolExecutor(objectWithTool, method, toolDescriptionFrom(method)))
                        .toolSpecification(toolSpecificationFrom(method))
                        .build());
            }
        }
    }

    private ToolDescription toolDescriptionFrom(Method method) {
        Tool toolAnnotation = method.getAnnotation(Tool.class);
        String name = Utils.isNullOrBlank(toolAnnotation.name()) ? method.getName() : toolAnnotation.name();
        String description = String.join("\n", toolAnnotation.value());

        ToolDescription.ToolDescriptionBuilder builder = ToolDescription.builder()
                .name(name)
                .description(description);

        for (Parameter parameter : method.getParameters()) {
            ai.swarmsolver.backend.app.tool.annotation.Parameter parameterAnnotation = getParameterAnnotation(method, parameter);
            builder.parameter(ToolParameter.builder()
                    .name(parameterAnnotation.name())
                    .description(parameterAnnotation.description())
                    .parameterType(parameterAnnotation.paramType())
                    .parameterTypeDetails(parameterAnnotation.paramTypeDetails())
                    .build());
        }

        return builder.build();
    }

    public List<ToolInfo> getToolInfos() {
        return List.copyOf(toolInfoByName.values());
    }

    private static ai.swarmsolver.backend.app.tool.annotation.Parameter getParameterAnnotation(Method method, Parameter parameter) {
        ai.swarmsolver.backend.app.tool.annotation.Parameter parameterAnnotation = parameter.getAnnotation(ai.swarmsolver.backend.app.tool.annotation.Parameter.class);
        if (parameterAnnotation==null) throw new IllegalArgumentException(String.format("method %s: parameter %s has no annotation ", method, parameter));
        if (parameterAnnotation.name()==null || parameterAnnotation.name().isEmpty()) throw new IllegalArgumentException(String.format("method %s: parameter %s parameter annotation has empty name", method, parameter));
        return parameterAnnotation;
    }

    public ToolExecutor getToolExecutor(String name) {
        return toolInfoByName.get(name).getToolExecutor();
    }

    public ToolSpecification getToolSpecification(String name) {
        return toolInfoByName.get(name).getToolSpecification();
    }

    public ToolDescription getToolDescription(String name) {
        return toolInfoByName.get(name).getToolDescription();
    }

    private ToolSpecification toolSpecificationFrom(Method method) {
        Tool toolAnnotation = (Tool)method.getAnnotation(Tool.class);
        String name = Utils.isNullOrBlank(toolAnnotation.name()) ? method.getName() : toolAnnotation.name();
        String description = String.join("\n", toolAnnotation.value());
        ToolSpecification.Builder builder = ToolSpecification.builder().name(name).description(description);
        Parameter[] parameters = method.getParameters();

        for(int idx = 0; idx < parameters.length; ++idx) {
            Parameter parameter = parameters[idx];
            ai.swarmsolver.backend.app.tool.annotation.Parameter parameterAnnotation = getParameterAnnotation(method, parameter);
            builder.addParameter(parameterAnnotation.name(), toJsonSchemaProperties(parameter));
        }

        return builder.build();
    }

    private Iterable<JsonSchemaProperty> toJsonSchemaProperties(Parameter parameter) {
        Class<?> type = parameter.getType();
        ai.swarmsolver.backend.app.tool.annotation.Parameter parameterAnnotation = parameter.getAnnotation(ai.swarmsolver.backend.app.tool.annotation.Parameter.class);
        JsonSchemaProperty description = parameterAnnotation == null ? null : JsonSchemaProperty.description(parameterAnnotation.description());
        if (type == String.class) {
            return removeNulls(JsonSchemaProperty.STRING, description);
        } else if (type != Boolean.TYPE && type != Boolean.class) {
            if (type != Byte.TYPE && type != Byte.class && type != Short.TYPE && type != Short.class && type != Integer.TYPE && type != Integer.class && type != Long.TYPE && type != Long.class && type != BigInteger.class) {
                if (type != Float.TYPE && type != Float.class && type != Double.TYPE && type != Double.class && type != BigDecimal.class) {
                    if (!type.isArray() && type != List.class && type != Set.class) {
                        return type.isEnum() ? removeNulls(JsonSchemaProperty.STRING, JsonSchemaProperty.enums((Object[])type.getEnumConstants()), description) : removeNulls(JsonSchemaProperty.OBJECT, description);
                    } else {
                        return removeNulls(JsonSchemaProperty.ARRAY, description);
                    }
                } else {
                    return removeNulls(JsonSchemaProperty.NUMBER, description);
                }
            } else {
                return removeNulls(JsonSchemaProperty.INTEGER, description);
            }
        } else {
            return removeNulls(JsonSchemaProperty.BOOLEAN, description);
        }
    }

    private Iterable<JsonSchemaProperty> removeNulls(JsonSchemaProperty... properties) {
        return (Iterable)Arrays.stream(properties).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<String> getToolNames() {
        return toolInfoByName.keySet().stream()
                .toList();
    }

}
