import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {TaskOverviewPageComponent} from "./task-overview-page/task-overview-page.component";
import {TaskPageComponent} from "./task-page/task-page.component";

export const routes: Routes = [
  { path: '', redirectTo: '/fe/home', pathMatch: 'full' },
  { path: 'fe/home', component: TaskOverviewPageComponent },
  { path: 'fe/task/:workspace/:id', component: TaskPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule {}
