package ai.swarmsolver.backend.app.tool.model;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.internal.Json;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolExecutor {
    private static final Logger log = LoggerFactory.getLogger(ToolExecutor.class);
    private final Object object;
    private final Method method;

    private final ToolDescription toolDescription;

    public ToolExecutor(Object object, Method method, ToolDescription toolDescription) {
        this.object = object;
        this.method = method;
        this.toolDescription = toolDescription;
    }

    public String execute(ToolExecutionRequest toolExecutionRequest) {
        log.debug("About to execute {}", toolExecutionRequest);
        Object[] arguments = this.prepareArguments(toolExecutionRequest.argumentsAsMap());

        try {
            String result = this.execute(arguments);
            log.debug("Tool execution result: {}", result);
            return result;
        } catch (IllegalAccessException var8) {
            try {
                this.method.setAccessible(true);
                String result = this.execute(arguments);
                log.debug("Tool execution result: {}", result);
                return result;
            } catch (IllegalAccessException var6) {
                throw new RuntimeException(var6);
            } catch (InvocationTargetException var7) {
                Throwable cause = var7.getCause();
                log.error("Error while executing tool", cause);
                return cause.getMessage();
            }
        } catch (InvocationTargetException var9) {
            Throwable cause = var9.getCause();
            log.error("Error while executing tool", cause);
            return cause.getMessage();
        }
    }

    private String execute(Object[] arguments) throws IllegalAccessException, InvocationTargetException {
        Object result = this.method.invoke(this.object, arguments);
        return this.method.getReturnType() == Void.TYPE ? "Success" : Json.toJson(result);
    }

    private Object[] prepareArguments(Map<String, Object> argumentsMap) {
        List<ToolParameter> parameters = this.toolDescription.getParameters();
        Object[] arguments = new Object[parameters.size()];

        for(int i = 0; i < parameters.size(); ++i) {
            String parameterName = parameters.get(i).getName();
            if (argumentsMap.containsKey(parameterName)) {
                Object argument = argumentsMap.get(parameterName);
                /*
                Class<?> parameterType = String.class;
                if (argument instanceof Double && parameterType != Double.class && parameterType != Double.TYPE) {
                    Double doubleValue = (Double)argument;
                    if (parameterType != Float.class && parameterType != Float.TYPE) {
                        if (parameterType == BigDecimal.class) {
                            argument = BigDecimal.valueOf(doubleValue);
                        }
                    } else {
                        if (doubleValue < -3.4028234663852886E38 || doubleValue > 3.4028234663852886E38) {
                            throw new IllegalArgumentException("Double value " + doubleValue + " is out of range for the float type");
                        }

                        argument = doubleValue.floatValue();
                    }

                    if (hasNoFractionalPart(doubleValue)) {
                        if (parameterType != Integer.class && parameterType != Integer.TYPE) {
                            if (parameterType != Long.class && parameterType != Long.TYPE) {
                                if (parameterType != Short.class && parameterType != Short.TYPE) {
                                    if (parameterType != Byte.class && parameterType != Byte.TYPE) {
                                        if (parameterType == BigInteger.class) {
                                            argument = BigDecimal.valueOf(doubleValue).toBigInteger();
                                        }
                                    } else {
                                        if (doubleValue < -128.0 || doubleValue > 127.0) {
                                            throw new IllegalArgumentException("Double value " + doubleValue + " is out of range for the byte type");
                                        }

                                        argument = doubleValue.byteValue();
                                    }
                                } else {
                                    if (doubleValue < -32768.0 || doubleValue > 32767.0) {
                                        throw new IllegalArgumentException("Double value " + doubleValue + " is out of range for the short type");
                                    }

                                    argument = doubleValue.shortValue();
                                }
                            } else {
                                if (doubleValue < -9.223372036854776E18 || doubleValue > 9.223372036854776E18) {
                                    throw new IllegalArgumentException("Double value " + doubleValue + " is out of range for the long type");
                                }

                                argument = doubleValue.longValue();
                            }
                        } else {
                            if (doubleValue < -2.147483648E9 || doubleValue > 2.147483647E9) {
                                throw new IllegalArgumentException("Double value " + doubleValue + " is out of range for the integer type");
                            }

                            argument = doubleValue.intValue();
                        }
                    }
                }
                */
                arguments[i] = argument;
            }
        }

        return arguments;
    }

    private static boolean hasNoFractionalPart(Double doubleValue) {
        return doubleValue.equals(Math.floor(doubleValue));
    }
}
