import { IOwner } from 'app/shared/model/owner.model';
import { IFoodItem } from 'app/shared/model/food-item.model';
import { IRoom } from 'app/shared/model/room.model';
import { IBill } from 'app/shared/model/bill.model';
import { IManager } from 'app/shared/model/manager.model';

export interface ICustomer {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  roomNumber?: number;
  owner?: IOwner;
  foodItems?: IFoodItem[];
  rooms?: IRoom[];
  bills?: IBill[];
  manager?: IManager;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public address?: string,
    public roomNumber?: number,
    public owner?: IOwner,
    public foodItems?: IFoodItem[],
    public rooms?: IRoom[],
    public bills?: IBill[],
    public manager?: IManager
  ) {}
}
