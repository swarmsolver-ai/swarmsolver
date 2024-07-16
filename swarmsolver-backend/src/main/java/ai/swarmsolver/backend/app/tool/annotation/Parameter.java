package ai.swarmsolver.backend.app.tool.annotation;

import ai.swarmsolver.backend.app.tool.model.ParameterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Parameter {
    String name() ;
    String description() default "";

    ParameterType paramType() default ParameterType.TEXT;

    String paramTypeDetails() default "";
}
