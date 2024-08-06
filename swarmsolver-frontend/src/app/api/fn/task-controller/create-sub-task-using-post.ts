/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TaskId } from '../../models/task-id';

export interface CreateSubTaskUsingPost$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * mainTaskId
 */
  mainTaskId?: string;

/**
 * parentTaskId
 */
  parentTaskId?: string;

/**
 * description
 */
  description?: string;

/**
 * agentName
 */
  agentName?: string;
}

export function createSubTaskUsingPost(http: HttpClient, rootUrl: string, params?: CreateSubTaskUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<TaskId>> {
  const rb = new RequestBuilder(rootUrl, createSubTaskUsingPost.PATH, 'post');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('mainTaskId', params.mainTaskId, {"style":"form"});
    rb.query('parentTaskId', params.parentTaskId, {"style":"form"});
    rb.query('description', params.description, {"style":"form"});
    rb.query('agentName', params.agentName, {"style":"form"});
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

createSubTaskUsingPost.PATH = '/api/task/subtask';
