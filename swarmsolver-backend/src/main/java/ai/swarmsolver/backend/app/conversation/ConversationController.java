package ai.swarmsolver.backend.app.conversation;

import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequestMapping("/api/conversation")
@Slf4j
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StringArrayResult read(String mainTaskId, String subTaskId, String conversationId) {
        TaskCoordinate taskCoordinate = TaskCoordinate.of(TaskId.of(mainTaskId), TaskId.of(subTaskId));
        ConversationCoordinate conversationCoordinate = ConversationCoordinate.of(taskCoordinate, ConversationId.of(conversationId));
        return StringArrayResult.builder()
                .content(conversationService.read(conversationCoordinate))
                .build();
    }

    @GetMapping("/messages/{subscriberId}")
    public SseEmitter streamSse(@PathVariable String subscriberId) {
        SseEmitter emitter = new SseEmitter(100000L);
        // log.info("Emitter created with timeout {} for subscriberId {}", emitter.getTimeout(), subscriberId);
        SseEmitterManager.addEmitter(subscriberId, emitter);

        // Set a timeout for the SSE connection (optional)
        emitter.onTimeout(() -> {
            //log.info("Emitter timed out");
            emitter.complete();
            SseEmitterManager.removeEmitter(subscriberId);
        });

        // Set a handler for client disconnect (optional)
        emitter.onCompletion(() -> {
            // log.info("Emitter completed");
            SseEmitterManager.removeEmitter(subscriberId);
        });

        return emitter;
    }
}
