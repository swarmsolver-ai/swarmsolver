package ai.swarmsolver.backend.app.conversation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private final MessageType messageType;
    private final String timeStamp;
    private final String from;
    private final String to;
    private final String messageBody;

}
