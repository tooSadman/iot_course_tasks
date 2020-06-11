import { IOwner } from 'app/shared/model/owner.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';

export interface IRoom {
  id?: number;
  number?: number;
  location?: string;
  owner?: IOwner;
  customer?: ICustomer;
  receptionist?: IReceptionist;
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public number?: number,
    public location?: string,
    public owner?: IOwner,
    public customer?: ICustomer,
    public receptionist?: IReceptionist
  ) {}
}
