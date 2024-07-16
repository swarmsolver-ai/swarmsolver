/* tslint:disable */
/* eslint-disable */
import { ToolParameterDto } from '../models/tool-parameter-dto';
export interface TooDescriptionDto {
  description?: string;
  name?: string;
  parameters?: Array<ToolParameterDto>;
  returnType?: 'SOURCE_CODE' | 'TEXT';
  returnTypeDetails?: string;
}
