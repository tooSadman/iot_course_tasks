import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { InventoryComponent } from './inventory.component';
import { InventoryDetailComponent } from './inventory-detail.component';
import { InventoryUpdateComponent } from './inventory-update.component';
import { InventoryDeleteDialogComponent } from './inventory-delete-dialog.component';
import { inventoryRoute } from './inventory.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(inventoryRoute)],
  declarations: [InventoryComponent, InventoryDetailComponent, InventoryUpdateComponent, InventoryDeleteDialogComponent],
  entryComponents: [InventoryDeleteDialogComponent]
})
export class BookingInventoryModule {}
