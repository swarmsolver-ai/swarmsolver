package ai.swarmsolver.backend.infra.scripting;

public interface ScriptBeanFactory<BEANTYPE> {

    BEANTYPE createInstance();

    String beanName();
}
