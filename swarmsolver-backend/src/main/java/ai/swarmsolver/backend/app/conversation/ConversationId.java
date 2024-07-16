package ai.swarmsolver.backend.app.conversation;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class ConversationId {

    private String identifier;

    public static ConversationId of(String identifier) {
        return new ConversationId(identifier);
    }
}
