/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TooDescriptionDto } from '../../models/too-description-dto';

export interface GetAllToolsUsingGet$Params {
}

export function getAllToolsUsingGet(http: HttpClient, rootUrl: string, params?: GetAllToolsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TooDescriptionDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllToolsUsingGet.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<TooDescriptionDto>>;
    })
  );
}

getAllToolsUsingGet.PATH = '/api/tools';
