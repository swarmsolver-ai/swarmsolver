package ai.swarmsolver.backend.infra.scripting;

import org.springframework.stereotype.Component;

@Component
public class LoggingBeanFactory implements ScriptBeanFactory<LoggingBean> {

    @Override
    public LoggingBean createInstance() {
        return new LoggingBean();
    }

    @Override
    public String beanName() {
        return "log";
    }
}
