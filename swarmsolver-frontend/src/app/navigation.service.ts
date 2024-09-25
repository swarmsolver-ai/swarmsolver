import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {WorkSpaceService} from "./work-space.service";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  constructor(private router: Router, private workSpaceService: WorkSpaceService){}

  toOverviewPage() {
    this.router.navigate(['fe','home', this.workSpaceService.selectedWorkSpace()]);
  }

  changeOverviewPageUrlWithoutRouting(workspace: string) {
    this.router.navigateByUrl(`/fe/home/${workspace}`);
  }

  toTaskPage(taskId: string) {
    this.router.navigate(['fe','task', this.workSpaceService.selectedWorkSpace(), taskId]);
  }

}
