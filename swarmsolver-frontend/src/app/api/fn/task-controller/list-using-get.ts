/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TaskSummaryDto } from '../../models/task-summary-dto';

export interface ListUsingGet$Params {
}

export function listUsingGet(http: HttpClient, rootUrl: string, params?: ListUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TaskSummaryDto>>> {
  const rb = new RequestBuilder(rootUrl, listUsingGet.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<TaskSummaryDto>>;
    })
  );
}

listUsingGet.PATH = '/api/task/tasks';
