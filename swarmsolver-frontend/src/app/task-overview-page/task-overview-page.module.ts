import {NgModule} from '@angular/core';
import {CoreModule} from "../core.module";
import {TaskOverviewPageComponent} from "./task-overview-page.component";
import {StyleguideModule} from "../styleguide/styleguide.module";
import {TaskDialogComponent} from "./task-dialog/task-dialog.component";
import { TagsEditComponent } from './tags-edit/tags-edit.component';

@NgModule({
  declarations: [
    TaskOverviewPageComponent,
    TaskDialogComponent,
    TagsEditComponent
  ],
  imports: [
    CoreModule,
    StyleguideModule
  ]
})

export class TaskOverviewPageModule {
}
