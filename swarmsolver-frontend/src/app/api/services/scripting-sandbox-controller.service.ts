/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { testUsingPost } from '../fn/scripting-sandbox-controller/test-using-post';
import { TestUsingPost$Params } from '../fn/scripting-sandbox-controller/test-using-post';


/**
 * Scripting Sandbox Controller
 */
@Injectable({ providedIn: 'root' })
export class ScriptingSandboxControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `testUsingPost()` */
  static readonly TestUsingPostPath = '/api/sandbox/script';

  /**
   * test.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `testUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  testUsingPost$Response(params?: TestUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return testUsingPost(this.http, this.rootUrl, params, context);
  }

  /**
   * test.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `testUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  testUsingPost(params?: TestUsingPost$Params, context?: HttpContext): Observable<void> {
    return this.testUsingPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

}
