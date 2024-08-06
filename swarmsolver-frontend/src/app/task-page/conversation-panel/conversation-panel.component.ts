import {ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ConversationService} from "./conversation.service";
import {ConversationCoordinate} from "../../api/models/conversation-coordinate";


interface Message {
  from: string
  to: string
  messageBody: string
  messageType: 'USER_MESSAGE' | 'AGENT_MESSAGE' | 'TOOL_EXECUTION_MESSAGE'
}

interface UserMessageBodyContents {
  text: string
}

interface UserMessageBody {
  contents: UserMessageBodyContents[]
}

interface AgentToolExecutionRequest {
  name: string
  arguments: string
}

export interface AgentMessageContent {
  text: string
  toolExecutionRequest: AgentToolExecutionRequest
}

export interface AgentMessage {
  content: AgentMessageContent,
  finishReason: 'TOOL_EXECUTION' | 'STOP'
}

export interface ToolExecutionMessage {
  text: string,
  toolName: string
}

export interface StepAddedEvent {
  taskId: string,
  stepName: string
}

@Component({
  selector: 'app-conversation-panel',
  templateUrl: './conversation-panel.component.html',
  styleUrl: './conversation-panel.component.css',
  providers: [ConversationService]
})
export class ConversationPanelComponent implements OnChanges, OnInit {

  constructor(private conversationService: ConversationService, private cdr: ChangeDetectorRef) {

  }

  @Input()
  conversationCoordinate: ConversationCoordinate | null = null;

  ngOnInit(): void {
    this.conversation$.subscribe(() => {
      setTimeout(() => {
        this.cdr.detectChanges();
      }, 50);
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.conversationService.getConversation(this.conversationCoordinate)
  }

  conversation$ = this.conversationService.conversation$

  asMessage(jsonMessage: string): Message {
    return <Message>JSON.parse(jsonMessage);
  }

  asUserMessageBody(messageBody: string): UserMessageBody {
    return <UserMessageBody>JSON.parse(messageBody)
  }

  asAgentMessage(messageBody: string): AgentMessage {
    return <AgentMessage>JSON.parse(messageBody)
  }

  asToolExecutionMessage(messageBody: string): ToolExecutionMessage {
    return <ToolExecutionMessage>JSON.parse(messageBody)
  }

  toUnquoted(input: string) {
    return input.replace(/\\n/g, '\n').replace(/\\r/g, '\r').replace(/"/g, '');
    ;
  }

  sampleMarkdown(): string {
    return `
    test test
    \`\`\`java
  // Step 1: Create WriteContentAction class

  package io.storydoc.swarmsolver.backend.app.action;

  import java.io.BufferedWriter;
  import java.io.FileWriter;
  import java.io.IOException;

  public class WriteContentAction {

  // Step 2: Define method to write content to a file
  public void writeContentToFile(String fileName, String content) {
  try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
  writer.write(content);
  } catch (IOException e) {
  e.printStackTrace();
  }
  }
  }
  \`\`\``;
  }

}
