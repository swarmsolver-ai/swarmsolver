package ai.swarmsolver.backend.app.tool.annotation;

import ai.swarmsolver.backend.app.tool.model.ParameterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Tool {
    String name() default "";

    String[] value() default {""};

    ParameterType returnType() default ParameterType.TEXT;

    String returnTypeDetails() default "";

}
