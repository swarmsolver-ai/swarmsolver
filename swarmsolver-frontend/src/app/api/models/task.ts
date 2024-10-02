/* tslint:disable */
/* eslint-disable */
import { AgentId } from '../models/agent-id';
import { TaskId } from '../models/task-id';
export interface Task {
  agentId?: AgentId;
  agentName?: string;
  archived?: boolean;
  description?: string;
  favorite?: boolean;
  id?: TaskId;
  state?: 'COMPLETED' | 'CREATED' | 'STARTED';
  subTasks?: Array<Task>;
  tags?: Array<string>;
  title?: string;
}
