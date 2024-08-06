import {Injectable, signal, WritableSignal} from "@angular/core";
import {TaskControllerService} from "./api/services/task-controller.service";
import {BehaviorSubject, Observable} from "rxjs";
import {AgentControllerService} from "./api/services/agent-controller.service";
import {AgentDescriptorDto} from "./api/models/agent-descriptor-dto";

@Injectable({
  providedIn: 'root'
})
export class WorkSpaceService {

  public selectedWorkSpace: WritableSignal<string> = signal('');

  public workSpaces = signal<string[]>([]);

  public agentNames = signal<string[]>([]);

  // add signal here

  constructor(private taskControllerService: TaskControllerService, private agentControllerService: AgentControllerService) {
    this.loadWorkSpaces();
  }

  public loadWorkSpaces() {
    return this.taskControllerService.getWorkSpacesUsingGet({})
      .subscribe(workSpaces => {
        this.workSpaces.set(workSpaces)
        if (workSpaces.length > 0) {
          this.selectWorkSpace(workSpaces[0])
        }
      })
  }

  public loadAgentDescriptors(workspaceName: string) {
    this.agentControllerService.getAgentDescriptorsUsingGet({ workspaceName }).subscribe((agentDescriptors: AgentDescriptorDto[]) => {
      const agentNamesArray: string[] = agentDescriptors.map(agent => agent.name!);
      this.agentNames.set(agentNamesArray);
    })
   }

  public selectWorkSpace(workspace: string) {
    this.selectedWorkSpace.set(workspace);
    this.loadAgentDescriptors(workspace);
  }

}
