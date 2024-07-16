package ai.swarmsolver.backend.app.conversation;

import ai.swarmsolver.backend.app.TestBase;
import ai.swarmsolver.backend.app.task.TaskServiceTestUtils;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ConversationServiceTest extends TestBase {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private TaskServiceTestUtils tasks;

    @Test
    public void create() {
        //  given a task
        TaskCoordinate taskCoordinate = tasks.createTaskWithSubTask();

        // when I create a conversation
        ConversationCoordinate conversationCoordinate = conversationService.initConversation(taskCoordinate);

        // then I receive an id
        assertNotNull(conversationCoordinate);

    }

    @Test
    public void add() {
        //  given a task
        TaskCoordinate taskCoordinate = tasks.createTaskWithSubTask();

        // given a conversation
        ConversationCoordinate conversationCoordinate = conversationService.initConversation(taskCoordinate);

        // when I add a line to the conversation
        String line = "Line - " + UUID.randomUUID();
        Message message = Message.builder()
                .messageBody(line)
                .build();
        conversationService.add(conversationCoordinate, message);

        // then the line is added
        List<String> lines = conversationService.read(conversationCoordinate);
        assertNotNull(lines);
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains(line));

    }


}