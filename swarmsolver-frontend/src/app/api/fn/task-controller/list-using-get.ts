/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TaskSummaryListDto } from '../../models/task-summary-list-dto';

export interface ListUsingGet$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * archived
 */
  archived?: boolean;

/**
 * favorite
 */
  favorite?: boolean;

/**
 * name
 */
  name?: string;

/**
 * field
 */
  field?: 'CREATED' | 'NAME';

/**
 * order
 */
  order?: 'ASCENDING' | 'DESCENDING';
}

export function listUsingGet(http: HttpClient, rootUrl: string, params?: ListUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<TaskSummaryListDto>> {
  const rb = new RequestBuilder(rootUrl, listUsingGet.PATH, 'get');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('archived', params.archived, {"style":"form"});
    rb.query('favorite', params.favorite, {"style":"form"});
    rb.query('name', params.name, {"style":"form"});
    rb.query('field', params.field, {"style":"form"});
    rb.query('order', params.order, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<TaskSummaryListDto>;
    })
  );
}

listUsingGet.PATH = '/api/task/tasks';
