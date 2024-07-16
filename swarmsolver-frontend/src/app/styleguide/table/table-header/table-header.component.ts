import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-table-header',
  templateUrl: './table-header.component.html',
  styleUrl: './table-header.component.css'
})
export class TableHeaderComponent {

  @Input()
  addActionLabel: string= '==>set addActionLabel<==';

  @Output()
  add = new EventEmitter<void>();

  addClicked() {
    this.add.emit();
  }
}
