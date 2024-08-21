package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileToolSettings {
    private String baseDir;
}
