/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { StringArrayResult } from '../../models/string-array-result';

export interface ReadUsingGet$Params {

/**
 * mainTaskId
 */
  mainTaskId?: string;

/**
 * subTaskId
 */
  subTaskId?: string;

/**
 * conversationId
 */
  conversationId?: string;
}

export function readUsingGet(http: HttpClient, rootUrl: string, params?: ReadUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<StringArrayResult>> {
  const rb = new RequestBuilder(rootUrl, readUsingGet.PATH, 'get');
  if (params) {
    rb.query('mainTaskId', params.mainTaskId, {"style":"form"});
    rb.query('subTaskId', params.subTaskId, {"style":"form"});
    rb.query('conversationId', params.conversationId, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<StringArrayResult>;
    })
  );
}

readUsingGet.PATH = '/api/conversation';
