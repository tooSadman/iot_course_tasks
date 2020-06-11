import { IOwner } from 'app/shared/model/owner.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IInventory } from 'app/shared/model/inventory.model';

export interface IManager {
  id?: number;
  name?: string;
  phone?: string;
  owner?: IOwner;
  customers?: ICustomer[];
  inventory?: IInventory;
}

export class Manager implements IManager {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public owner?: IOwner,
    public customers?: ICustomer[],
    public inventory?: IInventory
  ) {}
}
