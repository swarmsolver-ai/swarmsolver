package ai.swarmsolver.backend.app.tool.dto;

import ai.swarmsolver.backend.app.tool.model.ParameterType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TooDescriptionDTO {
    private final String name;
    private final String description;
    private final List<ToolParameterDTO> parameters;
    private final ParameterType returnType;
    private final String returnTypeDetails;
}
