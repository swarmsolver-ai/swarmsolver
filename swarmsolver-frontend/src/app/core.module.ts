import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {ApiModule} from "./api/api.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {environment} from "../environments/environment";
//import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {RouterModule} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {StyleguideModule} from "./styleguide/styleguide.module";
import { MarkdownModule } from 'ngx-markdown';

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule,
    CommonModule,
    ApiModule.forRoot({ rootUrl: environment.production ? '' : ('http://localhost:' + environment.port) }),
    //ApiModule,
    FormsModule,
    ReactiveFormsModule,
  //  FontAwesomeModule,
    RouterModule,
  ],
  exports: [
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
  //  FontAwesomeModule,
    RouterModule,

  ]
})
export class CoreModule {
}
