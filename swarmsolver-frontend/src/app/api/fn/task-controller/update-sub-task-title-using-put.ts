/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface UpdateSubTaskTitleUsingPut$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * mainTaskId
 */
  mainTaskId?: string;

/**
 * subTaskId
 */
  subTaskId?: string;

/**
 * title
 */
  title?: string;
}

export function updateSubTaskTitleUsingPut(http: HttpClient, rootUrl: string, params?: UpdateSubTaskTitleUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, updateSubTaskTitleUsingPut.PATH, 'put');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('mainTaskId', params.mainTaskId, {"style":"form"});
    rb.query('subTaskId', params.subTaskId, {"style":"form"});
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

updateSubTaskTitleUsingPut.PATH = '/api/task/subtask/title';
