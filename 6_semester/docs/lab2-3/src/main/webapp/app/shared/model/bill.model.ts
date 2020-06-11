import { IOwner } from 'app/shared/model/owner.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IBill {
  id?: number;
  number?: number;
  owner?: IOwner;
  receptionists?: IReceptionist[];
  customers?: ICustomer[];
}

export class Bill implements IBill {
  constructor(
    public id?: number,
    public number?: number,
    public owner?: IOwner,
    public receptionists?: IReceptionist[],
    public customers?: ICustomer[]
  ) {}
}
