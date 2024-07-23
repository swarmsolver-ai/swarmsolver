import {computed, effect, Injectable} from '@angular/core';
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

  private store = new BehaviorSubject<TaskSummaryDto[]>([])

  public task$: Observable<TaskSummaryDto[]> = this.store

  constructor(private taskControllerService: TaskControllerService, private workSpaceService: WorkSpaceService) {
    effect(() => {
      const selectedWorkspace = this.selectedWorkSpace();
      this.load();
    });
  }

  load() {
    this.taskControllerService.listUsingGet({workSpaceName: this.selectedWorkSpace()}).subscribe(value => {
      this.store.next(value)
    })
  }

  addTask(title: string) {
    this.taskControllerService.createMainTaskUsingPost({title, workSpaceName: this.selectedWorkSpace()}).subscribe(value => this.load())
  }

  deleteTask(taskId: string) {
    this.taskControllerService.deleteTaskUsingPut({taskId, workSpaceName: this.selectedWorkSpace()}).subscribe(value => this.load())
  }

  updateTaskTitle(taskId: TaskId, title: string) {
    this.taskControllerService.updateTaskTitleUsingPut({
      workSpaceName: this.selectedWorkSpace(),
      taskId: taskId.identifier,
      title
    }).subscribe(value => this.load())
  }


}
