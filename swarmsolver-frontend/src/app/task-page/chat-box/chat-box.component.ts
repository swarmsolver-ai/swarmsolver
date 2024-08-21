import {Component, EventEmitter, Input, Output} from '@angular/core';
import {StepAddedEvent} from "../conversation-panel/conversation-panel.component";
import {StepDialogComponentData, StepDialogComponentSpec} from "../step-dialog/step-dialog.component";
import {ModalService} from "../../styleguide/modal/modal-service";
import {Task} from "../../api/models/task";

@Component({
  selector: 'app-chat-box',
  templateUrl: './chat-box.component.html',
  styleUrl: './chat-box.component.css'
})
export class ChatBoxComponent {

  @Input()
  task!: Task

  @Output()
  stepAdded = new EventEmitter<StepAddedEvent>()

  constructor(private modalService: ModalService) {

  }

  spec: StepDialogComponentSpec | null = null;

  openDialog(spec: StepDialogComponentSpec) {
    this.spec = spec
    this.modalService.open(this.addStepDialogId())
  }

  closeDialog() {
    this.spec = null
    this.modalService.close(this.addStepDialogId())
  }

  addStepDialogId() {
    return 'add-step-dialog-id';
  }

  addStep() {
    this.openDialog({
      confirm: (data) => {
        this.confirmAdd(data);
        this.closeDialog()
      },
      cancel: () => this.closeDialog()
    })
  }


  private confirmAdd(data: StepDialogComponentData) {
    this.stepAdded.emit(<StepAddedEvent>{
      taskId: this.task.id!.identifier,
      stepName: data.name,
      agentName: data.agentName
    })
  }

  @Output()
  messageSent = new EventEmitter<string>()

  userMessage: string = '';

  calculateRows1(): number {
    const rows = Math.min(Math.ceil(this.userMessage.length / 50), 10); // Adjust 50 according to your desired character width
    return rows < 1 ? 1 : rows;
  }

  calculateRows(): number {
    const desiredCharWidth = 50; // Adjust this value based on your needs
    let rows = 0;
    for (const line of (this.userMessage || '').split('\n')) {
      rows += Math.ceil(line.length / desiredCharWidth);
    }
    return Math.min(rows, 10);
  }
  sendClicked() {
    if (this.userMessage.trim() !== '') {
      this.messageSent.emit(this.userMessage);
      this.userMessage = '';
    }
  }

  onPaste(event: ClipboardEvent) {
    this.scrollToBottom(event.target as HTMLTextAreaElement);
  }

  scrollToBottom(textArea: HTMLTextAreaElement) {
    setTimeout(() => {
      textArea.scrollTop = textArea.scrollHeight;
    }, 0);

  }
  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      if (event.ctrlKey) {
        this.sendClicked();
      } else {
        this.scrollToBottom(event.target as HTMLTextAreaElement)
      }
    }

  }
}
