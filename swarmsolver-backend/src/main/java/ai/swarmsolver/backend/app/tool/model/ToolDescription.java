package ai.swarmsolver.backend.app.tool.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@Data
public class ToolDescription {
    private final String name;
    private final String description;
    @Singular
    private final List<ToolParameter> parameters;
    private final ParameterType returnType;
    private final String returnTypeDetails;
}
