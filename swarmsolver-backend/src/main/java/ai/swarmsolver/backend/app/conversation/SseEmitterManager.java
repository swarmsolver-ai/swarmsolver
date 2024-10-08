package ai.swarmsolver.backend.app.conversation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
public class SseEmitterManager {
    private static final Logger logger = Logger.getLogger(SseEmitterManager.class.getName());
    private static final Map<String, SseEmitter> emitters = new HashMap<>();

    public static void addEmitter(String subscriberId, SseEmitter emitter) {
        emitters.put(subscriberId, emitter);
    }

    public static void removeEmitter(String subscriberId) {
        emitters.remove(subscriberId);
    }

    public static void sendSseEventToClients(String subscriberId, String data) {
        var emitter = emitters.get(subscriberId);
        if (emitter == null) {
            logger.warning("No client with subscriber Id " + subscriberId + " found!");
            return;
        }
        try {
            //logger.info("sending " + data);
            emitter.send(data);
        } catch (IOException e) {
            logger.warning("Error sending event to client: " + e.getMessage());
        }
    }
}
