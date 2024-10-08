import {Component, OnInit} from '@angular/core';
import {TaskOverviewService} from "./task-overview.service";
import {TaskSummaryDto} from "../api/models/task-summary-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {ModalService} from "../styleguide/modal/modal-service";
import {TaskDialogComponentSpec, TaskDialogState} from "./task-dialog/task-dialog.component";
import {ConfirmationDialogSpec} from "../styleguide/confirmation-dialog/confirmation-dialog.component";
import {WorkSpaceService} from "../work-space.service";
import {NavigationService} from "../navigation.service";
import {Order} from "../styleguide/table/table-column-sort/table-column-sort.component";

@Component({
  selector: 'app-task-overview-page',
  templateUrl: './task-overview-page.component.html',
  styleUrls: ['./task-overview-page.component.css'],
  providers: [TaskOverviewService]
})
export class TaskOverviewPageComponent implements OnInit {

  constructor(private service: TaskOverviewService, private route: ActivatedRoute, private modalService: ModalService, private workSpaceService: WorkSpaceService, private navigate: NavigationService) {
  }

  query = this.service.query;

  tasks = this.service.tasks;

  workSpaces = this.workSpaceService.workSpaces;

  selectedWorkSpace = this.workSpaceService.selectedWorkSpace;

  ngOnInit(): void {
    this.service.load();
    this.route.paramMap.subscribe((params) => {
      let workspace = params.get('workspace')
      if (workspace) {
        this.workSpaceService.selectWorkSpace(workspace);
      }
    });
  }

  openClicked($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    if (task && task.id) {
      this.navigate.toTaskPage(task.id);
    }
  }

  onEdit($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    this.openTaskDialog({
      mode: "EDIT",
      data: {
        taskId: {identifier: task.id},
        title: task.title!,
        tags: task.tags ? task.tags : []
      },
      confirm: (data) => {
        this.confirmEdit(data);
        this.closeTaskDialog();
      },
      cancel: () => this.closeTaskDialog()
    })
  }

  addClicked() {
    this.openTaskDialog({
      mode: "CREATE",
      data: {
        taskId: null,
        title: null,
        tags: []
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
    this.service.updateTaskTags(data.taskId!, data.tags!)
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

  archiveClicked($event: Event, task: TaskSummaryDto) {
    $event.preventDefault();
    this.service.archiveTask(task.id!, !task.archived);
  }

  onSelectWorkSpace($event: Event) {
    const selectElement = $event.target as HTMLSelectElement;
    let workspace = selectElement.value;
    this.workSpaceService.selectWorkSpace(workspace);
    this.navigate.changeOverviewPageUrlWithoutRouting(workspace);
  }

  updateArchivedFilter($event: Event) {
    const isChecked = ($event.target as HTMLInputElement).checked;
    this.service.updateArchivedFilter(isChecked);
  }

  filtersActive(): boolean {
     return this.activeFilterCount() > 0;
  }

  activeFilterCount(): number {
    let count = 0;
    if (this.query().filtering!.archived) count += 1;
    return count;
  }

  toggleSort(columnName: 'NAME' | 'CREATED', order: Order) {
    this.service.sort(columnName, order);
  }

  sortOrder(field: 'NAME' | 'CREATED') {
    let sorting = this.service.query().sorting!;
    return sorting.field == field ? sorting.order! : null;
  }
}
