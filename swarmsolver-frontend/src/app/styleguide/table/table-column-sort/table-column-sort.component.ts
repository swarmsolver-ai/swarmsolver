import {Component, EventEmitter, Input, Output} from '@angular/core';

export type Order = 'ASCENDING' | 'DESCENDING'

@Component({
  selector: 'app-table-column-sort',
  templateUrl: './table-column-sort.component.html',
  styleUrl: './table-column-sort.component.css'
})
export class TableColumnSortComponent {

  @Input()
  sortOrder: Order | null = null;

  @Output()
  toggled: EventEmitter<Order> = new EventEmitter<Order>();

  toggle() {
    console.log('toggled. emitting ', !this.sortOrder || this.sortOrder == 'DESCENDING'? 'ASCENDING' : 'DESCENDING')
    this.toggled.emit(!this.sortOrder || this.sortOrder == 'DESCENDING'? 'ASCENDING' : 'DESCENDING');
  }
}
