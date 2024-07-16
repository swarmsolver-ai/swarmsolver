/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Project } from '../../models/project';

export interface GetAllProjectsUsingGet$Params {
}

export function getAllProjectsUsingGet(http: HttpClient, rootUrl: string, params?: GetAllProjectsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Project>>> {
  const rb = new RequestBuilder(rootUrl, getAllProjectsUsingGet.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<Project>>;
    })
  );
}

getAllProjectsUsingGet.PATH = '/api/project';
