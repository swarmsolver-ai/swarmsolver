/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllToolsUsingGet } from '../fn/tool-controller/get-all-tools-using-get';
import { GetAllToolsUsingGet$Params } from '../fn/tool-controller/get-all-tools-using-get';
import { TooDescriptionDto } from '../models/too-description-dto';


/**
 * Tool Controller
 */
@Injectable({ providedIn: 'root' })
export class ToolControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllToolsUsingGet()` */
  static readonly GetAllToolsUsingGetPath = '/api/tools';

  /**
   * getAllTools.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllToolsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllToolsUsingGet$Response(params?: GetAllToolsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TooDescriptionDto>>> {
    return getAllToolsUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * getAllTools.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllToolsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllToolsUsingGet(params?: GetAllToolsUsingGet$Params, context?: HttpContext): Observable<Array<TooDescriptionDto>> {
    return this.getAllToolsUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<TooDescriptionDto>>): Array<TooDescriptionDto> => r.body)
    );
  }

}
