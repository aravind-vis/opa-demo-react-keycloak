import dayjs from 'dayjs';

export interface IProject {
  id?: number;
  name?: string;
  owner?: string;
  createdOn?: string;
}

export const defaultValue: Readonly<IProject> = {};
