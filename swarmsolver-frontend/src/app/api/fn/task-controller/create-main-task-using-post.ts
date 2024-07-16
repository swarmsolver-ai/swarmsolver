/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TaskId } from '../../models/task-id';

export interface CreateMainTaskUsingPost$Params {

/**
 * title
 */
  title?: string;
}

export function createMainTaskUsingPost(http: HttpClient, rootUrl: string, params?: CreateMainTaskUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<TaskId>> {
  const rb = new RequestBuilder(rootUrl, createMainTaskUsingPost.PATH, 'post');
  if (params) {
    rb.query('title', params.title, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<TaskId>;
    })
  );
}

createMainTaskUsingPost.PATH = '/api/task/maintask';
