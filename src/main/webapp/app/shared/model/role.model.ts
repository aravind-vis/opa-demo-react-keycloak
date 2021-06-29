export interface IRole {
  id?: number;
  roleName?: string;
  roleDescription?: string | null;
}

export const defaultValue: Readonly<IRole> = {};
