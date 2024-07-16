import {Component, Input, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface StepDialogComponentData {
  name: string
}

export interface StepDialogComponentSpec {
  cancel: () => void
  confirm: (data: StepDialogComponentData) => void

}


@Component({
  selector: 'app-step-dialog',
  templateUrl: './step-dialog.component.html',
  styleUrl: './step-dialog.component.css'
})
export class StepDialogComponent {

  @Input()
  spec: StepDialogComponentSpec | null = null;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec) {
      this.nameControl.setValue(null)
    }
  }
  cancel() {
    this.spec?.cancel()
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, [Validators.required])
  })

  get nameControl(): FormControl {
    return <FormControl>this.formGroup.get('name')
  }


  confirm() {
    this.spec?.confirm(this.formGroup.value)
  }


}
