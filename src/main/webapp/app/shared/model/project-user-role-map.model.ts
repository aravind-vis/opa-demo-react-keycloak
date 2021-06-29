import { IProject } from 'app/shared/model/project.model';
import { IRole } from 'app/shared/model/role.model';

export interface IProjectUserRoleMap {
  id?: number;
  user?: string;
  projectId?: IProject | null;
  roleId?: IRole | null;
}

export const defaultValue: Readonly<IProjectUserRoleMap> = {};
