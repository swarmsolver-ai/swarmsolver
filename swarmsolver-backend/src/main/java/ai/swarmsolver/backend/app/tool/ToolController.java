package ai.swarmsolver.backend.app.tool;

import ai.swarmsolver.backend.app.tool.dto.TooDescriptionDTO;
import ai.swarmsolver.backend.app.tool.dto.ToolParameterDTO;
import ai.swarmsolver.backend.app.tool.model.ToolDescription;
import ai.swarmsolver.backend.app.tool.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final ToolService toolRegistry;

    @Autowired
    public ToolController(ToolService toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TooDescriptionDTO> getAllTools() {
        return toolRegistry.getToolNames().stream()
                .map(toolRegistry::getToolDescription)
                .map(this::toDTO)
                .toList();
    }

    private TooDescriptionDTO toDTO(ToolDescription toolDescription) {
        return TooDescriptionDTO.builder()
                .name(toolDescription.getName())
                .description(toolDescription.getDescription())
                .parameters(toolDescription.getParameters().stream()
                        .map(param -> ToolParameterDTO.builder()
                                .name(param.getName())
                                .description(param.getDescription())
                                .parameterType(param.getParameterType())
                                .parameterTypeDetails(param.getParameterTypeDetails())
                                .build())
                        .collect(Collectors.toList())
                )
                .returnType(toolDescription.getReturnType())
                .returnTypeDetails(toolDescription.getReturnTypeDetails())
                .build();

    }
}
