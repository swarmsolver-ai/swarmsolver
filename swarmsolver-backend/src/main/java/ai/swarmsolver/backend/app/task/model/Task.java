package ai.swarmsolver.backend.app.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ai.swarmsolver.backend.app.agent.domain.AgentId;
import ai.swarmsolver.backend.app.conversation.ConversationId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private TaskState state;
    private TaskId id;
    private String title;
    private String description;
    private List<Task> subTasks;
    private AgentId agentId;
    private String agentName;
    private boolean archived;
    private boolean favorite;
    private List<String> tags;

    @JsonIgnore
    public boolean isComposite() {
        return subTasks != null || subTasks.size() > 0;
    }


}
