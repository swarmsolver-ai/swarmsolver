/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { readUsingGet } from '../fn/conversation-controller/read-using-get';
import { ReadUsingGet$Params } from '../fn/conversation-controller/read-using-get';
import { SseEmitter } from '../models/sse-emitter';
import { streamSseUsingGet } from '../fn/conversation-controller/stream-sse-using-get';
import { StreamSseUsingGet$Params } from '../fn/conversation-controller/stream-sse-using-get';
import { StringArrayResult } from '../models/string-array-result';


/**
 * Conversation Controller
 */
@Injectable({ providedIn: 'root' })
export class ConversationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `readUsingGet()` */
  static readonly ReadUsingGetPath = '/api/conversation';

  /**
   * read.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `readUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  readUsingGet$Response(params?: ReadUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<StringArrayResult>> {
    return readUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * read.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `readUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  readUsingGet(params?: ReadUsingGet$Params, context?: HttpContext): Observable<StringArrayResult> {
    return this.readUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<StringArrayResult>): StringArrayResult => r.body)
    );
  }

  /** Path part for operation `streamSseUsingGet()` */
  static readonly StreamSseUsingGetPath = '/api/conversation/messages/{subscriberId}';

  /**
   * streamSse.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `streamSseUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  streamSseUsingGet$Response(params: StreamSseUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<SseEmitter>> {
    return streamSseUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * streamSse.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `streamSseUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  streamSseUsingGet(params: StreamSseUsingGet$Params, context?: HttpContext): Observable<SseEmitter> {
    return this.streamSseUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<SseEmitter>): SseEmitter => r.body)
    );
  }

}
