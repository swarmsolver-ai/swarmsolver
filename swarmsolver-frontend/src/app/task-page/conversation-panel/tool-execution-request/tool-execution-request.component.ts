import {Component, Input} from '@angular/core';
import {AgentToolExecutionRequest, ToolExecutionMessage} from "../conversation-panel.component";

@Component({
  selector: 'app-tool-execution-request',
  templateUrl: './tool-execution-request.component.html',
  styleUrl: './tool-execution-request.component.css'
})
export class ToolExecutionRequestComponent {
  @Input()
  toolExecutionRequest!: AgentToolExecutionRequest;
}
