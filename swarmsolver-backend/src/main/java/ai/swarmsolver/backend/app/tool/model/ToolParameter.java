package ai.swarmsolver.backend.app.tool.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ToolParameter {
    String name;
    String description;
    ParameterType parameterType;
    String parameterTypeDetails;
}
