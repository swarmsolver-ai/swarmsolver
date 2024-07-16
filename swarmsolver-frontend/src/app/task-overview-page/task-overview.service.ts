import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {TaskControllerService} from "../api/services/task-controller.service";
import {TaskSummaryDto} from "../api/models/task-summary-dto";
import {TaskId} from "../api/models/task-id";

@Injectable({
  providedIn: 'root'
})
export class TaskOverviewService {

  private store = new BehaviorSubject<TaskSummaryDto[]>([])

  public task$: Observable<TaskSummaryDto[]> = this.store

  constructor(private taskControllerService: TaskControllerService) {
  }

  load() {
    this.taskControllerService.listUsingGet({}).subscribe(value => {
      this.store.next(value)
    })
  }

  addTask(title: string) {
    this.taskControllerService.createMainTaskUsingPost({title}).subscribe(value => this.load())
  }

  deleteTask(taskId: string) {
    this.taskControllerService.deleteTaskUsingPut({taskId}).subscribe(value => this.load())
  }

  updateTaskTitle(taskId: TaskId, title: string) {
    this.taskControllerService.updateTaskTitleUsingPut({
      taskId: taskId.identifier,
      title
    }).subscribe(value => this.load())
  }


}
