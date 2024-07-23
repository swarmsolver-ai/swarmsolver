package ai.swarmsolver.backend.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationProperties {
    private Map<String, String> workspaces;
}
