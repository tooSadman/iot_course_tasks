import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceptionist } from 'app/shared/model/receptionist.model';
import { ReceptionistService } from './receptionist.service';

@Component({
  templateUrl: './receptionist-delete-dialog.component.html'
})
export class ReceptionistDeleteDialogComponent {
  receptionist?: IReceptionist;

  constructor(
    protected receptionistService: ReceptionistService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receptionistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('receptionistListModification');
      this.activeModal.close();
    });
  }
}
