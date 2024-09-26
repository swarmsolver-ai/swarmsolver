/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface UpdateTaskFavoriteUsingPut$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * taskId
 */
  taskId?: string;

/**
 * favorite
 */
  favorite?: boolean;
}

export function updateTaskFavoriteUsingPut(http: HttpClient, rootUrl: string, params?: UpdateTaskFavoriteUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, updateTaskFavoriteUsingPut.PATH, 'put');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('taskId', params.taskId, {"style":"form"});
    rb.query('favorite', params.favorite, {"style":"form"});
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

updateTaskFavoriteUsingPut.PATH = '/api/task/task/favorite';
