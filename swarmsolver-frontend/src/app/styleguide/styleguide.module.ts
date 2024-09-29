import {NgModule} from '@angular/core';
import {CoreModule} from "../core.module";

import { HeaderComponent } from './header/header.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { ModalComponent } from './modal/modal/modal.component';
import { DialogComponent } from './dialog/dialog.component';
import { TableHeaderComponent } from './table/table-header/table-header.component';
import { TableCardComponent } from './table/table-card/table-card.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { TableColumnSortComponent } from './table/table-column-sort/table-column-sort.component';

@NgModule({
  declarations: [
    HeaderComponent,
    SideBarComponent,
    ModalComponent,
    DialogComponent,
    TableHeaderComponent,
    TableCardComponent,
    ConfirmationDialogComponent,
    TableColumnSortComponent
  ],
  imports: [
    CoreModule
  ],
    exports: [
        HeaderComponent,
        SideBarComponent,
        ModalComponent,
        DialogComponent,
        TableHeaderComponent,
        TableCardComponent,
        ConfirmationDialogComponent,
        TableColumnSortComponent
    ]
})
export class StyleguideModule {
}
