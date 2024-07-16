import {ApplicationRef, DoBootstrap, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {CoreModule} from "./core.module";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TaskOverviewPageModule} from "./task-overview-page/task-overview-page.module";
import {StyleguideModule} from "./styleguide/styleguide.module";
import {TaskPageModule} from "./task-page/task-page.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    StyleguideModule,
    CoreModule,
    TaskOverviewPageModule,
    TaskPageModule,
    BrowserModule,
    AppRoutingModule,
  ]
})
export class AppModule implements DoBootstrap {
  ngDoBootstrap(appRef: ApplicationRef): void {
    appRef.bootstrap(AppComponent);
  }
}
