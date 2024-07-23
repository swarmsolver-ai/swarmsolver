/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface ErrorUsingPost$Params {
}

export function errorUsingPost(http: HttpClient, rootUrl: string, params?: ErrorUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<{
[key: string]: {
};
}>> {
  const rb = new RequestBuilder(rootUrl, errorUsingPost.PATH, 'post');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<{
      [key: string]: {
      };
      }>;
    })
  );
}

errorUsingPost.PATH = '/error';
