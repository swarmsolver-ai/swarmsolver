import {Component, Input} from '@angular/core';
import {AgentSummaryDto} from "../../api/models/agent-summary-dto";
import {Task} from "../../api/models/task";
import {StepAddedEvent} from "../conversation-panel/conversation-panel.component";
import {TaskService} from "../task.service";

@Component({
  selector: 'app-conversation-container',
  templateUrl: './conversation-container.component.html',
  styleUrl: './conversation-container.component.css'
})
export class ConversationContainerComponent {

  @Input()
  task! : Task

  @Input()
  agent: AgentSummaryDto | null = null;

  constructor(private service: TaskService) {}

  stepAdded($event: StepAddedEvent) {
    this.service.addSubTask($event.taskId, $event.taskId, $event.stepName, $event.agentName);
  }


  onUserChatMessage(message: string) {
    this.service.userMessage(message);
  }
}
