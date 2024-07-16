package ai.swarmsolver.backend.infra.scripting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingBean {

    public void info(String s) {
        log.info(s);
    }

    public void info(String s, Object o) {
        log.info(s, o);
    }

    public  void info(String s, Object o, Object o1) {
        log.info(s, o, o1);
    }

    public  void info(String s, Object... objects) {
        log.info(s, objects);
    }

    public  void info(String s, Throwable throwable) {
        log.info(s, throwable);
    }
}
