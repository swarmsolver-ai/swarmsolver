import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {WorkSpaceService} from "../../work-space.service";
import {NavigationService} from "../../navigation.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
    constructor(private navigate: NavigationService) {}

    toOverview($event: Event) {
      $event.preventDefault();
      this.navigate.toOverviewPage();
    }
}
