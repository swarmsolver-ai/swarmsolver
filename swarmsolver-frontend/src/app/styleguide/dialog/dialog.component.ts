import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css'
})
export class DialogComponent {

  @Input()
  title: string= '==>Set Title<==';

  @Input()
  complete: boolean= true;

  @Output()
  cancel = new EventEmitter<void>()

  @Output()
  confirm = new EventEmitter<void>()

  cancelClicked() {
    this.cancel.emit();
  }

  confirmClicked() {
    this.confirm.emit();
  }

}
