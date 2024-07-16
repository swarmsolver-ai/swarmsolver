import {Component, OnInit} from '@angular/core';
import {TaskOverviewService} from "./task-overview.service";
import {Observable} from "rxjs";
import {TaskSummaryDto} from "../api/models/task-summary-dto";
import {Router} from "@angular/router";
import {ModalService} from "../styleguide/modal/modal-service";
import {TaskDialogComponentSpec, TaskDialogState} from "./task-dialog/task-dialog.component";
import {ConfirmationDialogSpec} from "../styleguide/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-task-overview-page',
  templateUrl: './task-overview-page.component.html',
  styleUrls: ['./task-overview-page.component.css'],
  providers: [TaskOverviewService]
})
export class TaskOverviewPageComponent implements OnInit {

  constructor(private service: TaskOverviewService, private router: Router, private modalService: ModalService) {
  }

  task$: Observable<TaskSummaryDto[]> = this.service.task$

  ngOnInit(): void {
    this.service.load()
  }

  openClicked($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    if (task && task.id) {
      this.router.navigate(['fe','task', task.id]);
    }
  }

  onEdit($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    this.openTaskDialog({
      mode: "EDIT",
      data: {
        taskId: {identifier: task.id},
        title: task.title!
      },
      confirm: (data) => {
        this.confirmEdit(data);
        this.closeTaskDialog()
      },
      cancel: () => this.closeTaskDialog()
    })
  }

  addClicked() {
    this.openTaskDialog({
      mode: "CREATE",
      data: {
        taskId: null,
        title: null
      },

      confirm: (data) => {
        this.confirmAdd(data);
        this.closeTaskDialog()
      },
      cancel: () => this.closeTaskDialog()
    })
  }

  taskDialogSpec: TaskDialogComponentSpec | null = null;

  openTaskDialog(spec: TaskDialogComponentSpec) {
    this.taskDialogSpec = spec
    this.modalService.open(this.taskDialogId())
  }

  closeTaskDialog() {
    this.taskDialogSpec = null
    this.modalService.close(this.taskDialogId())
  }

  taskDialogId() {
    return 'add-task-dialog';
  }

  private confirmAdd(data: TaskDialogState) {
    this.service.addTask(data.title!)
  }

  private confirmEdit(data: TaskDialogState) {
    this.service.updateTaskTitle(data.taskId!, data.title!)
  }

  deleteTaskDialogId() {
    return 'delete-task-dialog';
  }

  deleteClicked($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    this.openConfirmDeletionDialog({
      title: `Delete Task ${task.title}`,
      confirmLabel: 'Delete',
      question: 'Are you sure you want to delete this task?',
      confirm: () => {
        this.service.deleteTask(task.id!);
        this.closeConfirmDeletionDialog();
      },
      cancel: () => this.closeConfirmDeletionDialog()
    });
  }

  confirmDeleteSpec: ConfirmationDialogSpec | null = null;

  openConfirmDeletionDialog(spec: ConfirmationDialogSpec) {
    this.confirmDeleteSpec = spec;
    this.modalService.open(this.deleteTaskDialogId())
  }

  closeConfirmDeletionDialog() {
    this.confirmDeleteSpec = null;
    this.modalService.close(this.deleteTaskDialogId())
  }


}
