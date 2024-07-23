/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface ErrorUsingPatch$Params {
}

export function errorUsingPatch(http: HttpClient, rootUrl: string, params?: ErrorUsingPatch$Params, context?: HttpContext): Observable<StrictHttpResponse<{
[key: string]: {
};
}>> {
  const rb = new RequestBuilder(rootUrl, errorUsingPatch.PATH, 'patch');
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

errorUsingPatch.PATH = '/error';
