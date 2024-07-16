package ai.swarmsolver.backend.infra.scripting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sandbox")
public class ScriptingSandboxController {

    private final ScriptingService scriptingService;

    public ScriptingSandboxController(ScriptingService scriptingService) {
        this.scriptingService = scriptingService;
    }

    @PostMapping(value = "/script", produces = MediaType.APPLICATION_JSON_VALUE)
    public void test(String fileName) {
        scriptingService.runFile(fileName);
    }

}
