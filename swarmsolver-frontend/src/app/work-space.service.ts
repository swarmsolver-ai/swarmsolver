import {Injectable, signal, WritableSignal} from "@angular/core";
import {TaskControllerService} from "./api/services/task-controller.service";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WorkSpaceService {

  public selectedWorkSpace: WritableSignal<string> = signal('');

  public workSpaces = signal<string[]>([]);

  constructor(private taskControllerService: TaskControllerService) {
    this.loadWorkSpaces();
  }

  public loadWorkSpaces() {
    return this.taskControllerService.getWorkSpacesUsingGet({})
      .subscribe(workSpaces => {
        this.workSpaces.set(workSpaces)
        if (workSpaces.length > 0) {
          this.selectedWorkSpace.set(workSpaces[0])
        }
      })
  }

  public selectWorkSpace(workspace: string) {
    this.selectedWorkSpace.set(workspace);
  }

}
