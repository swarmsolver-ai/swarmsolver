import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-table-header',
  templateUrl: './table-header.component.html',
  styleUrl: './table-header.component.css'
})
export class TableHeaderComponent {

  @Input()
  title: string = '';

  @Input()
  subTitle: string = '';
}
