import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.BookingCustomerModule)
      },
      {
        path: 'receptionist',
        loadChildren: () => import('./receptionist/receptionist.module').then(m => m.BookingReceptionistModule)
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.BookingInventoryModule)
      },
      {
        path: 'room',
        loadChildren: () => import('./room/room.module').then(m => m.BookingRoomModule)
      },
      {
        path: 'bill',
        loadChildren: () => import('./bill/bill.module').then(m => m.BookingBillModule)
      },
      {
        path: 'food-item',
        loadChildren: () => import('./food-item/food-item.module').then(m => m.BookingFoodItemModule)
      },
      {
        path: 'manager',
        loadChildren: () => import('./manager/manager.module').then(m => m.BookingManagerModule)
      },
      {
        path: 'owner',
        loadChildren: () => import('./owner/owner.module').then(m => m.BookingOwnerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BookingEntityModule {}
