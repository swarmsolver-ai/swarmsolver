/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { AgentDescriptorDto } from '../models/agent-descriptor-dto';
import { getAgentDescriptorsUsingGet } from '../fn/agent-controller/get-agent-descriptors-using-get';
import { GetAgentDescriptorsUsingGet$Params } from '../fn/agent-controller/get-agent-descriptors-using-get';


/**
 * Agent Controller
 */
@Injectable({ providedIn: 'root' })
export class AgentControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAgentDescriptorsUsingGet()` */
  static readonly GetAgentDescriptorsUsingGetPath = '/api/agent';

  /**
   * getAgentDescriptors.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAgentDescriptorsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAgentDescriptorsUsingGet$Response(params?: GetAgentDescriptorsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AgentDescriptorDto>>> {
    return getAgentDescriptorsUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * getAgentDescriptors.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAgentDescriptorsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAgentDescriptorsUsingGet(params?: GetAgentDescriptorsUsingGet$Params, context?: HttpContext): Observable<Array<AgentDescriptorDto>> {
    return this.getAgentDescriptorsUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<AgentDescriptorDto>>): Array<AgentDescriptorDto> => r.body)
    );
  }

}
