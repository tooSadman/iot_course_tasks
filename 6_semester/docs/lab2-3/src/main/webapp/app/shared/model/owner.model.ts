import { IManager } from 'app/shared/model/manager.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IFoodItem } from 'app/shared/model/food-item.model';
import { IBill } from 'app/shared/model/bill.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IOwner {
  id?: number;
  name?: string;
  phone?: string;
  manager?: IManager;
  receptionist?: IReceptionist;
  customer?: ICustomer;
  foodItem?: IFoodItem;
  bill?: IBill;
  room?: IRoom;
}

export class Owner implements IOwner {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public manager?: IManager,
    public receptionist?: IReceptionist,
    public customer?: ICustomer,
    public foodItem?: IFoodItem,
    public bill?: IBill,
    public room?: IRoom
  ) {}
}
