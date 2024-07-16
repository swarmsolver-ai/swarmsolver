import {Component, EventEmitter, Input, Output} from '@angular/core';
import {StepDialogComponentData} from "../../task-page/step-dialog/step-dialog.component";

export interface ConfirmationDialogSpec {
  title: string
  question: string
  confirmLabel: string
  cancel: () => void
  confirm: () => void
}

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrl: './confirmation-dialog.component.css'
})
export class ConfirmationDialogComponent {

  @Input()
  spec: ConfirmationDialogSpec | null = null;

  cancelClicked() {
    this.spec?.cancel();
  }

  confirmClicked() {
    this.spec?.confirm();
  }

}
