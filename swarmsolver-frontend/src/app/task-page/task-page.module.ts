import {CoreModule} from "../core.module";
import {StyleguideModule} from "../styleguide/styleguide.module";
import {NgModule} from "@angular/core";
import {TaskPageComponent} from "./task-page.component";
import { TaskListComponent } from './task-list/task-list.component';
import { ConversationPanelComponent } from './conversation-panel/conversation-panel.component';
import {MarkdownModule} from "ngx-markdown";
import { StepDialogComponent } from './step-dialog/step-dialog.component';
import { ConversationContainerComponent } from './conversation-container/conversation-container.component';
import { ChatBoxComponent } from './chat-box/chat-box.component';
import { AgentBadgeComponent } from './conversation-panel/agent-badge/agent-badge.component';
import { ToolExecutionRequestComponent } from './conversation-panel/tool-execution-request/tool-execution-request.component';

@NgModule({
  declarations: [
    TaskPageComponent,
    TaskListComponent,
    ConversationPanelComponent,
    StepDialogComponent,
    ConversationContainerComponent,
    ChatBoxComponent,
    AgentBadgeComponent,
    ToolExecutionRequestComponent,

  ],
  imports: [
    CoreModule,
    StyleguideModule,
    MarkdownModule.forRoot(),
  ]
})

export class TaskPageModule { }
