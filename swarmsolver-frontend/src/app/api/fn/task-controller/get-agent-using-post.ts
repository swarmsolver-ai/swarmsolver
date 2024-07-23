/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AgentSummaryDto } from '../../models/agent-summary-dto';

export interface GetAgentUsingPost$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * mainTaskId
 */
  mainTaskId?: string;

/**
 * taskId
 */
  taskId?: string;
}

export function getAgentUsingPost(http: HttpClient, rootUrl: string, params?: GetAgentUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<AgentSummaryDto>> {
  const rb = new RequestBuilder(rootUrl, getAgentUsingPost.PATH, 'post');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('mainTaskId', params.mainTaskId, {"style":"form"});
    rb.query('taskId', params.taskId, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AgentSummaryDto>;
    })
  );
}

getAgentUsingPost.PATH = '/api/task/agent';
