import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TaskId} from "../../api/models/task-id";

export interface TaskDialogState {
  taskId: TaskId | null
  title: string | null
}

export interface TaskDialogComponentSpec {
  mode: 'EDIT' | 'CREATE'
  data: TaskDialogState
  cancel: () => void
  confirm: (data: TaskDialogState) => void
}


@Component({
  selector: 'app-task-dialog',
  templateUrl: './task-dialog.component.html',
  styleUrls: ['./task-dialog.component.css']
})
export class TaskDialogComponent implements OnChanges {

  @Input()
  spec: TaskDialogComponentSpec | null = null;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec) {
      this.formGroup.setValue(this.spec.data!)
    }
  }

  formGroup: FormGroup = new FormGroup({
    taskId: new FormControl(null),
    title: new FormControl(null, [Validators.required])
  })

  get taskIdControl(): FormControl {
    return <FormControl>this.formGroup.get('taskId')
  }

  get titleControl(): FormControl {
    return <FormControl>this.formGroup.get('title')
  }

  cancel() {
    this.spec?.cancel()
  }

  confirm() {
    this.spec?.confirm(this.formGroup.value)
  }

  complete(): boolean {
    return this.formGroup.valid
  }

}
