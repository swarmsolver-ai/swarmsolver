package ai.swarmsolver.backend.app.tool.dto;

import ai.swarmsolver.backend.app.tool.model.ParameterType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ToolParameterDTO {
    String name;
    String description;
    ParameterType parameterType;
    String parameterTypeDetails;
}
