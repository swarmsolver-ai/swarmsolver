/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface UpdateTaskArchivedUsingPut$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * taskId
 */
  taskId?: string;

/**
 * archived
 */
  archived?: boolean;
}

export function updateTaskArchivedUsingPut(http: HttpClient, rootUrl: string, params?: UpdateTaskArchivedUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, updateTaskArchivedUsingPut.PATH, 'put');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('taskId', params.taskId, {"style":"form"});
    rb.query('archived', params.archived, {"style":"form"});
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

updateTaskArchivedUsingPut.PATH = '/api/task/task/archive';
