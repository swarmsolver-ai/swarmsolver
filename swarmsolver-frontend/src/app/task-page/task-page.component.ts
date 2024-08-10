import {Component} from '@angular/core';
import {TaskService} from "./task.service";
import {ActivatedRoute} from "@angular/router";
import {StepAddedEvent} from "./conversation-panel/conversation-panel.component";
import {WorkSpaceService} from "../work-space.service";

@Component({
  selector: 'app-task-page',
  templateUrl: './task-page.component.html',
  styleUrl: './task-page.component.css',
  providers: [TaskService]
})
export class TaskPageComponent {

  constructor(private service: TaskService, private route: ActivatedRoute, private workspaceService: WorkSpaceService) {
  }

  task$ = this.service.task$

  selection$ = this.service.step$

  agent$ = this.service.agent$

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let workspace = params.get('workspace')
      if (workspace) {
        this.workspaceService.selectWorkSpace(workspace);
      }
      let taskId = params.get('id')
      if (taskId) {
        this.service.openTask(taskId)
      }
    });
  }

  stepSelected(id: string) {
    this.service.selectStep(id)
  }

  stepAdded($event: StepAddedEvent) {
    this.service.addSubTask($event.taskId, $event.taskId, $event.stepName, $event.agentName)
  }


}
