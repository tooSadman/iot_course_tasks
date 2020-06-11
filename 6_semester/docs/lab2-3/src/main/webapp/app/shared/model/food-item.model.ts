import { IOwner } from 'app/shared/model/owner.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IFoodItem {
  id?: number;
  name?: string;
  owner?: IOwner;
  customer?: ICustomer;
}

export class FoodItem implements IFoodItem {
  constructor(public id?: number, public name?: string, public owner?: IOwner, public customer?: ICustomer) {}
}
