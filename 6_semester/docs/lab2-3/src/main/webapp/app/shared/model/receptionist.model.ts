import { IOwner } from 'app/shared/model/owner.model';
import { IRoom } from 'app/shared/model/room.model';
import { IBill } from 'app/shared/model/bill.model';

export interface IReceptionist {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  owner?: IOwner;
  managedRooms?: IRoom[];
  bill?: IBill;
}

export class Receptionist implements IReceptionist {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public address?: string,
    public owner?: IOwner,
    public managedRooms?: IRoom[],
    public bill?: IBill
  ) {}
}
