import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Task} from "../../api/models/task";
import {StepDialogComponentData, StepDialogComponentSpec} from "../step-dialog/step-dialog.component";
import {ModalService} from "../../styleguide/modal/modal-service";
import {StepAddedEvent} from "../conversation-panel/conversation-panel.component";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent {

  @Input()
  task! : Task

  @Input()
  selection: Task | null = null;

  @Output()
  stepSelected = new EventEmitter<string>()

  @Output()
  stepAdded = new EventEmitter<StepAddedEvent>()

  constructor(private modalService: ModalService) {
  }

  selectStep(id: string) {
    this.stepSelected.emit(id)
  }

  spec: StepDialogComponentSpec | null = null;

  openDialog(spec: StepDialogComponentSpec) {
    this.spec = spec
    this.modalService.open(this.addStepDialogId())
  }

  closeDialog() {
    this.spec = null
    this.modalService.close(this.addStepDialogId())
  }
  addStepDialogId() {
    return 'task-list-add-step-dialog-id';
  }

  addStep() {
    this.openDialog({
      confirm: (data) => { this.confirmAdd(data); this.closeDialog() },
      cancel: () => this.closeDialog()
    })
  }

  private confirmAdd(data: StepDialogComponentData) {
    this.stepAdded.emit(<StepAddedEvent>{ taskId: this.task.id!.identifier, stepName: data.name })
  }


}
