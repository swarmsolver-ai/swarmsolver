import {computed, effect, Injectable, signal} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {TaskControllerService} from "../api/services/task-controller.service";
import {TaskSummaryDto} from "../api/models/task-summary-dto";
import {TaskId} from "../api/models/task-id";
import {WorkSpaceService} from "../work-space.service";

@Injectable({
  providedIn: 'root'
})
export class TaskOverviewService {

  private selectedWorkSpace = this.workSpaceService.selectedWorkSpace;

  filterState = signal<{ archived: boolean }>({ archived: false });

  private store = new BehaviorSubject<TaskSummaryDto[]>([])

  public task$: Observable<TaskSummaryDto[]> = this.store

  constructor(private taskControllerService: TaskControllerService, private workSpaceService: WorkSpaceService) {
    effect(() => {
      const selectedWorkspace = this.selectedWorkSpace();
      this.load();
    });
  }

  load() {
    this.taskControllerService.listUsingGet({
      workSpaceName: this.selectedWorkSpace(),
      ...this.filterState(),
    }).subscribe(value => {
      this.store.next(value)
    })
  }

  addTask(title: string) {
    this.taskControllerService.createMainTaskUsingPost({title, workSpaceName: this.selectedWorkSpace()}).subscribe(value => this.load())
  }

  deleteTask(taskId: string) {
    this.taskControllerService.deleteTaskUsingPut({taskId, workSpaceName: this.selectedWorkSpace()}).subscribe(value => this.load())
  }

  archiveTask(taskId: string, archived: boolean) {
    this.taskControllerService.updateTaskArchivedUsingPut({
      taskId,
      workSpaceName: this.selectedWorkSpace(),
      archived
    }).subscribe(value => this.load())
  }

  updateTaskTitle(taskId: TaskId, title: string) {
    this.taskControllerService.updateTaskTitleUsingPut({
      workSpaceName: this.selectedWorkSpace(),
      taskId: taskId.identifier,
      title
    }).subscribe(value => this.load())
  }

  updateArchivedFilter( archived: boolean) {
    let filterState = this.filterState()
    this.filterState.set({
      ... this.filterState(),
      archived,
    });
    this.load();
  }

}
