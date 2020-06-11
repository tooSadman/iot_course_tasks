import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceptionist } from 'app/shared/model/receptionist.model';

@Component({
  selector: 'jhi-receptionist-detail',
  templateUrl: './receptionist-detail.component.html'
})
export class ReceptionistDetailComponent implements OnInit {
  receptionist: IReceptionist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receptionist }) => (this.receptionist = receptionist));
  }

  previousState(): void {
    window.history.back();
  }
}
