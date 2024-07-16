import {NgModule} from '@angular/core';
import {CoreModule} from "../core.module";
import {TaskOverviewPageComponent} from "./task-overview-page.component";
import {StyleguideModule} from "../styleguide/styleguide.module";
import {TaskDialogComponent} from "./task-dialog/task-dialog.component";

@NgModule({
  declarations: [
    TaskOverviewPageComponent,
    TaskDialogComponent
  ],
  imports: [
    CoreModule,
    StyleguideModule
  ]
})

export class TaskOverviewPageModule {
}
