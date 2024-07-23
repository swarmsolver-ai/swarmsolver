/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface TestUsingPost$Params {

/**
 * workSpaceName
 */
  workSpaceName?: string;

/**
 * fileName
 */
  fileName?: string;
}

export function testUsingPost(http: HttpClient, rootUrl: string, params?: TestUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, testUsingPost.PATH, 'post');
  if (params) {
    rb.query('workSpaceName', params.workSpaceName, {"style":"form"});
    rb.query('fileName', params.fileName, {"style":"form"});
  }

  return http.request(
    rb.build({ responseType: 'text', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
    })
  );
}

testUsingPost.PATH = '/api/sandbox/script';
