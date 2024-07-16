package ai.swarmsolver.backend.app.conversation;

import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor

public class ConversationCoordinate {
    private TaskCoordinate taskCoordinate;
    private ConversationId conversationId;

    private ConversationCoordinate() {
    }

    static public ConversationCoordinate of(TaskCoordinate taskCoordinate, ConversationId conversationId) {
        return new ConversationCoordinate(taskCoordinate, conversationId);
    }
}
