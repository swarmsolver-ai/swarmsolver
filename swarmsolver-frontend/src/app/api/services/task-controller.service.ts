/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { AgentSummaryDto } from '../models/agent-summary-dto';
import { createMainTaskUsingPost } from '../fn/task-controller/create-main-task-using-post';
import { CreateMainTaskUsingPost$Params } from '../fn/task-controller/create-main-task-using-post';
import { createSubTaskUsingPost } from '../fn/task-controller/create-sub-task-using-post';
import { CreateSubTaskUsingPost$Params } from '../fn/task-controller/create-sub-task-using-post';
import { deleteTaskUsingPut } from '../fn/task-controller/delete-task-using-put';
import { DeleteTaskUsingPut$Params } from '../fn/task-controller/delete-task-using-put';
import { getAgentUsingPost } from '../fn/task-controller/get-agent-using-post';
import { GetAgentUsingPost$Params } from '../fn/task-controller/get-agent-using-post';
import { getMainTaskUsingGet } from '../fn/task-controller/get-main-task-using-get';
import { GetMainTaskUsingGet$Params } from '../fn/task-controller/get-main-task-using-get';
import { getWorkSpacesUsingGet } from '../fn/task-controller/get-work-spaces-using-get';
import { GetWorkSpacesUsingGet$Params } from '../fn/task-controller/get-work-spaces-using-get';
import { listUsingGet } from '../fn/task-controller/list-using-get';
import { ListUsingGet$Params } from '../fn/task-controller/list-using-get';
import { Task } from '../models/task';
import { TaskId } from '../models/task-id';
import { TaskSummaryDto } from '../models/task-summary-dto';
import { updateSubTaskTitleUsingPut } from '../fn/task-controller/update-sub-task-title-using-put';
import { UpdateSubTaskTitleUsingPut$Params } from '../fn/task-controller/update-sub-task-title-using-put';
import { updateTaskTitleUsingPut } from '../fn/task-controller/update-task-title-using-put';
import { UpdateTaskTitleUsingPut$Params } from '../fn/task-controller/update-task-title-using-put';
import { userMessageUsingPost } from '../fn/task-controller/user-message-using-post';
import { UserMessageUsingPost$Params } from '../fn/task-controller/user-message-using-post';


/**
 * Task Controller
 */
@Injectable({ providedIn: 'root' })
export class TaskControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAgentUsingPost()` */
  static readonly GetAgentUsingPostPath = '/api/task/agent';

  /**
   * getAgent.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAgentUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAgentUsingPost$Response(params?: GetAgentUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<AgentSummaryDto>> {
    return getAgentUsingPost(this.http, this.rootUrl, params, context);
  }

  /**
   * getAgent.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAgentUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAgentUsingPost(params?: GetAgentUsingPost$Params, context?: HttpContext): Observable<AgentSummaryDto> {
    return this.getAgentUsingPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<AgentSummaryDto>): AgentSummaryDto => r.body)
    );
  }

  /** Path part for operation `getMainTaskUsingGet()` */
  static readonly GetMainTaskUsingGetPath = '/api/task/maintask';

  /**
   * getMainTask.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMainTaskUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMainTaskUsingGet$Response(params?: GetMainTaskUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Task>> {
    return getMainTaskUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * getMainTask.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getMainTaskUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMainTaskUsingGet(params?: GetMainTaskUsingGet$Params, context?: HttpContext): Observable<Task> {
    return this.getMainTaskUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Task>): Task => r.body)
    );
  }

  /** Path part for operation `createMainTaskUsingPost()` */
  static readonly CreateMainTaskUsingPostPath = '/api/task/maintask';

  /**
   * createMainTask.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createMainTaskUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMainTaskUsingPost$Response(params?: CreateMainTaskUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<TaskId>> {
    return createMainTaskUsingPost(this.http, this.rootUrl, params, context);
  }

  /**
   * createMainTask.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createMainTaskUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMainTaskUsingPost(params?: CreateMainTaskUsingPost$Params, context?: HttpContext): Observable<TaskId> {
    return this.createMainTaskUsingPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<TaskId>): TaskId => r.body)
    );
  }

  /** Path part for operation `createSubTaskUsingPost()` */
  static readonly CreateSubTaskUsingPostPath = '/api/task/subtask';

  /**
   * createSubTask.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSubTaskUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createSubTaskUsingPost$Response(params?: CreateSubTaskUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<TaskId>> {
    return createSubTaskUsingPost(this.http, this.rootUrl, params, context);
  }

  /**
   * createSubTask.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createSubTaskUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createSubTaskUsingPost(params?: CreateSubTaskUsingPost$Params, context?: HttpContext): Observable<TaskId> {
    return this.createSubTaskUsingPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<TaskId>): TaskId => r.body)
    );
  }

  /** Path part for operation `updateSubTaskTitleUsingPut()` */
  static readonly UpdateSubTaskTitleUsingPutPath = '/api/task/subtask/title';

  /**
   * updateSubTaskTitle.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateSubTaskTitleUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateSubTaskTitleUsingPut$Response(params?: UpdateSubTaskTitleUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return updateSubTaskTitleUsingPut(this.http, this.rootUrl, params, context);
  }

  /**
   * updateSubTaskTitle.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateSubTaskTitleUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateSubTaskTitleUsingPut(params?: UpdateSubTaskTitleUsingPut$Params, context?: HttpContext): Observable<void> {
    return this.updateSubTaskTitleUsingPut$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `deleteTaskUsingPut()` */
  static readonly DeleteTaskUsingPutPath = '/api/task/task/remove';

  /**
   * deleteTask.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteTaskUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteTaskUsingPut$Response(params?: DeleteTaskUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return deleteTaskUsingPut(this.http, this.rootUrl, params, context);
  }

  /**
   * deleteTask.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteTaskUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteTaskUsingPut(params?: DeleteTaskUsingPut$Params, context?: HttpContext): Observable<void> {
    return this.deleteTaskUsingPut$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `updateTaskTitleUsingPut()` */
  static readonly UpdateTaskTitleUsingPutPath = '/api/task/task/title';

  /**
   * updateTaskTitle.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateTaskTitleUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateTaskTitleUsingPut$Response(params?: UpdateTaskTitleUsingPut$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return updateTaskTitleUsingPut(this.http, this.rootUrl, params, context);
  }

  /**
   * updateTaskTitle.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateTaskTitleUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateTaskTitleUsingPut(params?: UpdateTaskTitleUsingPut$Params, context?: HttpContext): Observable<void> {
    return this.updateTaskTitleUsingPut$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `listUsingGet()` */
  static readonly ListUsingGetPath = '/api/task/tasks';

  /**
   * list.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  listUsingGet$Response(params?: ListUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TaskSummaryDto>>> {
    return listUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * list.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `listUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listUsingGet(params?: ListUsingGet$Params, context?: HttpContext): Observable<Array<TaskSummaryDto>> {
    return this.listUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<TaskSummaryDto>>): Array<TaskSummaryDto> => r.body)
    );
  }

  /** Path part for operation `userMessageUsingPost()` */
  static readonly UserMessageUsingPostPath = '/api/task/usermsg';

  /**
   * userMessage.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `userMessageUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  userMessageUsingPost$Response(params?: UserMessageUsingPost$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return userMessageUsingPost(this.http, this.rootUrl, params, context);
  }

  /**
   * userMessage.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `userMessageUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  userMessageUsingPost(params?: UserMessageUsingPost$Params, context?: HttpContext): Observable<void> {
    return this.userMessageUsingPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `getWorkSpacesUsingGet()` */
  static readonly GetWorkSpacesUsingGetPath = '/api/task/workspaces';

  /**
   * getWorkSpaces.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getWorkSpacesUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getWorkSpacesUsingGet$Response(params?: GetWorkSpacesUsingGet$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<string>>> {
    return getWorkSpacesUsingGet(this.http, this.rootUrl, params, context);
  }

  /**
   * getWorkSpaces.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getWorkSpacesUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getWorkSpacesUsingGet(params?: GetWorkSpacesUsingGet$Params, context?: HttpContext): Observable<Array<string>> {
    return this.getWorkSpacesUsingGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<string>>): Array<string> => r.body)
    );
  }

}
