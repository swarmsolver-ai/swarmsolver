package ai.swarmsolver.backend.app;

import ai.swarmsolver.backend.app.agent.app.AgentSummaryDTO;
import ai.swarmsolver.backend.app.agent.domain.AgentRepository;
import ai.swarmsolver.backend.app.task.TaskServiceTestUtils;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MainScenarioIT extends TestBase {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskServiceTestUtils taskStepDefs;

    @Autowired
    AgentRepository agentRepository;

    @Test
    public void runAgent() {

        // given a main task and a subtask
        TaskCoordinate taskCoordinate = taskStepDefs.createTaskWithSubTask();

        // when I send a message to the agent associated with the subtask
        taskService.userMessage(taskCoordinate, "hello");
        AgentSummaryDTO agentSummaryDTO = taskService.getAgentSummary(taskCoordinate);
        printDirStructure("agent message sent");

        // and the agent cache is reset (simulate restart)
        agentRepository.resetCache();
        taskService.userMessage(taskCoordinate, "hello (bis)");
        printDirStructure("agent mesage sent after restart");



    }

}
