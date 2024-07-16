/* tslint:disable */
/* eslint-disable */
import { AgentId } from '../models/agent-id';
import { TaskId } from '../models/task-id';
export interface Task {
  agentId?: AgentId;
  agentName?: string;
  description?: string;
  id?: TaskId;
  state?: 'COMPLETED' | 'CREATED' | 'STARTED';
  subTasks?: Array<Task>;
  title?: string;
}
