/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface UpdateTaskTitleUsingPut$Params {

/**
 * taskId
 */
  taskId?: string;

/**
 * title
 */
  title?: string;
}

export function updateTaskTitleUsingPut(http: HttpClient, rootUrl: string, params?: UpdateTaskTitleUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, updateTaskTitleUsingPut.PATH, 'put');
  if (params) {
    rb.query('taskId', params.taskId, {"style":"form"});
    rb.query('title', params.title, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'text', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
    })
  );
}

updateTaskTitleUsingPut.PATH = '/api/task/task/title';
