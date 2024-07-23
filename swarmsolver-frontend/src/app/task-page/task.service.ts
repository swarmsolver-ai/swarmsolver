import {Injectable} from '@angular/core';
import {TaskControllerService} from "../api/services/task-controller.service";
import {Task} from "../api/models/task";
import {BehaviorSubject, distinctUntilChanged, Observable, Subject} from "rxjs";
import {TaskId} from "../api/models/task-id";
import {AgentSummaryDto} from "../api/models/agent-summary-dto";
import {map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {WorkSpaceService} from "../work-space.service";

interface TaskStoreState {
  task: Task | null
  selectedStep: Task | null
  agent: AgentSummaryDto | null
}

export interface SimpleResponse {
  content: string;
}

@Injectable()
export class TaskService {

  private store = new BehaviorSubject<TaskStoreState>({task: null, selectedStep: null, agent: null})

  public task$: Observable<Task | null> = this.store.pipe(
    map(state => state.task),
    distinctUntilChanged(),
  )

  public step$: Observable<Task | null> = this.store.pipe(
    map(state => state.selectedStep),
    distinctUntilChanged(),
  )

  public agent$: Observable<AgentSummaryDto | null> = this.store.pipe(
    map(state => state.agent),
    distinctUntilChanged(),
  )

  constructor(private taskControllerService: TaskControllerService, private workSpaceService: WorkSpaceService) {
  }

  openTask(mainTaskId: string) {
    this.taskControllerService.getMainTaskUsingGet({
      workSpaceName: this.workSpaceService.selectedWorkSpace(),
      taskId: mainTaskId}).subscribe(mainTask => {
      this.store.next({
        task: mainTask,
        selectedStep: null,
        agent: null
      })
      if (mainTask.subTasks) {
        let defaultStep = mainTask.subTasks[0]
        if (defaultStep && defaultStep.id?.identifier) {
          this.selectStep(defaultStep.id.identifier)
        }
      }
    })
  }

  selectStep(stepId: string) {
    let state = this.store.value
    let steps = state.task?.subTasks
    if (steps) {
      let selectedStep = steps.filter((subTask) => subTask.id?.identifier === stepId)[0]
      this.store.next({
        ...state,
        selectedStep: selectedStep
      })
      if (selectedStep) this.startAgentForStep()
    }
  }

  startAgentForStep() {
    let state = this.store.value
    let mainTaskId = state.task?.id
    let stepId = state.selectedStep?.id

    if (mainTaskId && stepId) {
      this.taskControllerService.getAgentUsingPost({
        workSpaceName: this.workSpaceService.selectedWorkSpace(),
        mainTaskId: mainTaskId.identifier,
        taskId: stepId.identifier
      }).subscribe(agent => {
          this.store.next({
            ...state,
            agent
          })
        }
      )
    }
  }


  reloadTask(taskId: string) {
      return this.taskControllerService.getMainTaskUsingGet({
        workSpaceName: this.workSpaceService.selectedWorkSpace(),
        taskId
      }).subscribe(task => {
        let state = this.store.value
        this.store.next({
          ...state,
          task: task
        })
      })
   }

  reloadTask$(taskId: string): Observable<Task> {
    return this.taskControllerService.getMainTaskUsingGet({
      workSpaceName: this.workSpaceService.selectedWorkSpace(),
      taskId
    }).pipe(
      map(task => {
        let state = this.store.value;
        this.store.next({
          ...state,
          task: task
        });
        return task;
      })
    );
  }


  addSubTask(mainTaskId: string, parentTaskId: string, description: string) {
    this.taskControllerService.createSubTaskUsingPost({
      workSpaceName: this.workSpaceService.selectedWorkSpace(),
      mainTaskId,
      parentTaskId,
      description
    }).subscribe(newTaskId => {
      this.reloadTask$(mainTaskId).subscribe(task => {
        this.selectStep(newTaskId.identifier!)
      })
    })
  }

  updateTaskTitle(taskId: TaskId, title: string) {
    this.taskControllerService.updateTaskTitleUsingPut({
      workSpaceName: this.workSpaceService.selectedWorkSpace(),
      taskId: taskId.identifier,
      title
    }).subscribe(value => {
      this.reloadTask(taskId.identifier!)
    })
  }

  updateSubTaskTitle(mainTaskId: string, subTaskId: string, title: string) {
    this.taskControllerService.updateSubTaskTitleUsingPut({
      workSpaceName: this.workSpaceService.selectedWorkSpace(),
      mainTaskId, subTaskId, title
    }).subscribe(value => {
      this.reloadTask(mainTaskId)
    })
  }

  userMessage(message: string) {
    let state = this.store.value
    let mainTaskId = state.task?.id!.identifier!
    let subTaskId = state.selectedStep?.id?.identifier!
    if (mainTaskId && subTaskId) {
      this.taskControllerService.userMessageUsingPost({
        body: {
          workSpaceName: this.workSpaceService.selectedWorkSpace(),
          mainTaskId,
          subTaskId,
          message
        }
      }).subscribe(value => {
        // this.reloadTask(mainTaskId)
      })
    }

  }


}
