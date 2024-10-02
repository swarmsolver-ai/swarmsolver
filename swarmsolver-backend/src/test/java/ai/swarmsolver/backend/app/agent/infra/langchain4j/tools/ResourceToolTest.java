package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import ai.swarmsolver.backend.app.TestBase;
import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import ai.swarmsolver.backend.app.agent.domain.AgentWorkSpace;
import ai.swarmsolver.backend.app.agent.infra.langchain4j.workspace.WorkspaceAccess;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.service.TaskService;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Ignore
public class ResourceToolTest extends TestBase {

    @Autowired
    DirectoryStructure directoryStructure;

    @Autowired
    TaskService taskService;

    @Test
    public void workspaceResource() {
        String workspace = "workspace";
        TaskCoordinate taskCoordinate = taskService.createMainTask(workspace, "title");
        printDirStructure("after create main task");

        AgentWorkSpace agentWorkSpace = AgentWorkSpace.of(directoryStructure, AgentCoordinate.of(taskCoordinate, null));
        WorkspaceAccess workspaceAccess = new WorkspaceAccess(agentWorkSpace);

        String name = "global-analysis";
        ResourcesTool resourcesTool = ResourcesTool.builder()
                .scope(ResourceScope.WORKSPACE)
                .resource(ResourceDescriptor.builder()
                        .name(name)
                        .build())
                .workspaceAccess(workspaceAccess)
                .build();

        String content = "content-" + UUID.randomUUID();
        resourcesTool.writeResource(name, content);
        printDirStructure("after resource creation");


        String contentRead = resourcesTool.readResource(name);
        Assert.assertEquals(contentRead, content);
    }

}
