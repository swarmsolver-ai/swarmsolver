/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Task } from '../../models/task';

export interface GetMainTaskUsingGet$Params {

/**
 * taskId
 */
  taskId?: string;
}

export function getMainTaskUsingGet(http: HttpClient, rootUrl: string, params?: GetMainTaskUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Task>> {
  const rb = new RequestBuilder(rootUrl, getMainTaskUsingGet.PATH, 'get');
  if (params) {
    rb.query('taskId', params.taskId, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Task>;
    })
  );
}

getMainTaskUsingGet.PATH = '/api/task/maintask';
