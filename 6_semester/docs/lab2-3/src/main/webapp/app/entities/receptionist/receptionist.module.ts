import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { ReceptionistComponent } from './receptionist.component';
import { ReceptionistDetailComponent } from './receptionist-detail.component';
import { ReceptionistUpdateComponent } from './receptionist-update.component';
import { ReceptionistDeleteDialogComponent } from './receptionist-delete-dialog.component';
import { receptionistRoute } from './receptionist.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(receptionistRoute)],
  declarations: [ReceptionistComponent, ReceptionistDetailComponent, ReceptionistUpdateComponent, ReceptionistDeleteDialogComponent],
  entryComponents: [ReceptionistDeleteDialogComponent]
})
export class BookingReceptionistModule {}
