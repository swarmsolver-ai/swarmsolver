
import {Injectable} from '@angular/core';
import {ConversationControllerService} from "../../api/services/conversation-controller.service";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {ConversationCoordinate} from "../../api/models/conversation-coordinate";
import{ environment} from "../../../environments/environment";
import { Location } from '@angular/common';
import {WorkSpaceService} from "../../work-space.service";

@Injectable()
export class ConversationService  {

  private store = new BehaviorSubject<string[]|null>(null)

  public conversation$: Observable<string[]|null> = this.store

  constructor(private conversationControllerService : ConversationControllerService, private workSpaceService: WorkSpaceService) {
  }

  getConversation(conversationCoordinate: ConversationCoordinate | null) {
    if (!conversationCoordinate) {
      this.store.next([])
    } else {
      this.conversationControllerService.readUsingGet({
        workSpaceName: this.workSpaceService.selectedWorkSpace(),
        mainTaskId: conversationCoordinate.taskCoordinate!.mainTaskId!.identifier,
        subTaskId: conversationCoordinate.taskCoordinate!.subTaskId!.identifier,
        conversationId: conversationCoordinate.conversationId!.identifier
      }).subscribe(value => {
        this.store.next(value.content!)
        this.connectToSSE(conversationCoordinate.conversationId!.identifier!)
      })
    }
  }


  private eventSource: EventSource | undefined;
  private sseDataSubject: Subject<string> = new Subject<string>();
  private static retryCount = 0;
  private static readonly MAX_RETRIES = 5;

  private connectToSSE(conversationId: string) {
    const port = environment.production? location.port : environment.port;
    this.eventSource = new EventSource(`http://localhost:${port}/api/conversation/messages/${conversationId}`);
    this.eventSource.onmessage = event => {
      if (this.store.value) {
        this.store.next([...this.store.value, event.data])
      } else {
        this.store.next([event.data])
      }
      // this.sseDataSubject.next(event.data);
    };

    this.eventSource.onerror = error => {
      console.log('error', error);
    };
  }

}
