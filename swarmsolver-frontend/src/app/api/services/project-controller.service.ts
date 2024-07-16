/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllProjectsUsingGet } from '../fn/project-controller/get-all-projects-using-get';
import { GetAllProjectsUsingGet$Params } from '../fn/project-controller/get-all-projects-using-get';
import { Project } from '../models/project';


/**
 * Project Controller
 */
@Injectable({ providedIn: 'root' })
export class ProjectControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllProjectsUsingGet()` */
  static readonly GetAllProjectsUsingGetPath = '/api/project';

  /**
   * getAllProjects.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllProjectsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProjectsUsingGet$Response(params?: GetAllProjectsUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Project>>> {
    return getAllProjectsUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * getAllProjects.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllProjectsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProjectsUsingGet(params?: GetAllProjectsUsingGet$Params, context?: HttpContext): Observable<Array<Project>> {
    return this.getAllProjectsUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Project>>): Array<Project> => r.body)
    );
  }

}
