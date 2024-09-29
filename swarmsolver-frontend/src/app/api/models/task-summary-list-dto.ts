/* tslint:disable */
/* eslint-disable */
import { FilterDto } from '../models/filter-dto';
import { SortingDto } from '../models/sorting-dto';
import { TaskSummaryDto } from '../models/task-summary-dto';
export interface TaskSummaryListDto {
  filtering?: FilterDto;
  sorting?: SortingDto;
  summaries?: Array<TaskSummaryDto>;
  workspace?: string;
}
