/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AgentDescriptorDto } from '../../models/agent-descriptor-dto';

export interface GetAgentDescriptorsUsingGet$Params {

/**
 * workspaceName
 */
  workspaceName?: string;
}

export function getAgentDescriptorsUsingGet(http: HttpClient, rootUrl: string, params?: GetAgentDescriptorsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AgentDescriptorDto>>> {
  const rb = new RequestBuilder(rootUrl, getAgentDescriptorsUsingGet.PATH, 'get');
  if (params) {
    rb.query('workspaceName', params.workspaceName, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<AgentDescriptorDto>>;
    })
  );
}

getAgentDescriptorsUsingGet.PATH = '/api/agent';
