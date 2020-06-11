import { IManager } from 'app/shared/model/manager.model';

export interface IInventory {
  id?: number;
  type?: string;
  status?: string;
  managers?: IManager[];
}

export class Inventory implements IInventory {
  constructor(public id?: number, public type?: string, public status?: string, public managers?: IManager[]) {}
}
