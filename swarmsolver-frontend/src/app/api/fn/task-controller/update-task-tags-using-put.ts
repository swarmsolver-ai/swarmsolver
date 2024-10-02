/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface UpdateTaskTagsUsingPut$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * taskId
 */
  taskId?: string;
      body?: Array<string>
}

export function updateTaskTagsUsingPut(http: HttpClient, rootUrl: string, params?: UpdateTaskTagsUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, updateTaskTagsUsingPut.PATH, 'put');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('taskId', params.taskId, {"style":"form"});
    rb.body(params.body, 'application/json');
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

updateTaskTagsUsingPut.PATH = '/api/task/task/tags';
