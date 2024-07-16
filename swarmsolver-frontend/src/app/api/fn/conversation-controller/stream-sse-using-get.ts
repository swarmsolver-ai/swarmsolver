/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SseEmitter } from '../../models/sse-emitter';

export interface StreamSseUsingGet$Params {

/**
 * subscriberId
 */
  subscriberId: string;
}

export function streamSseUsingGet(http: HttpClient, rootUrl: string, params: StreamSseUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<SseEmitter>> {
  const rb = new RequestBuilder(rootUrl, streamSseUsingGet.PATH, 'get');
  if (params) {
    rb.path('subscriberId', params.subscriberId, {"style":"simple"});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<SseEmitter>;
    })
  );
}

streamSseUsingGet.PATH = '/api/conversation/messages/{subscriberId}';
