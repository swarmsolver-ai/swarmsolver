package ai.swarmsolver.backend.infra.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Service
public class ScriptingService {

    private final List<ScriptBeanFactory<?>> beanFactories;

    public ScriptingService(List<ScriptBeanFactory<?>> beanFactories) {
        this.beanFactories = beanFactories;
    }

    @SneakyThrows
    public Object runFile(String fileName) {
        Binding binding = new Binding();

        for(ScriptBeanFactory<?> beanFactory: beanFactories) {
            binding.setVariable(beanFactory.beanName(), beanFactory.createInstance());
        }

        GroovyShell shell = new GroovyShell(this.getClass().getClassLoader(), binding);
        String scriptContent = new String(Files.readAllBytes(Paths.get(fileName)));
        return shell.evaluate(scriptContent);

    }

}
