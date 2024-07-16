package ai.swarmsolver.backend.app.keys;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import ai.swarmsolver.backend.infra.scripting.ScriptBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class KeysBeanFactory implements ScriptBeanFactory<KeysBean> {

    private  final DirectoryStructure directoryStructure;

    public KeysBeanFactory(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
    }

    @Override
    public KeysBean createInstance() {
        return new KeysBean(directoryStructure);
    }

    @Override
    public String beanName() {
        return "keys";
    }
}
