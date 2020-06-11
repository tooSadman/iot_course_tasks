import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceptionist } from 'app/shared/model/receptionist.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReceptionistService } from './receptionist.service';
import { ReceptionistDeleteDialogComponent } from './receptionist-delete-dialog.component';

@Component({
  selector: 'jhi-receptionist',
  templateUrl: './receptionist.component.html'
})
export class ReceptionistComponent implements OnInit, OnDestroy {
  receptionists: IReceptionist[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected receptionistService: ReceptionistService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.receptionists = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.receptionistService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReceptionist[]>) => this.paginateReceptionists(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.receptionists = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReceptionists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReceptionist): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReceptionists(): void {
    this.eventSubscriber = this.eventManager.subscribe('receptionistListModification', () => this.reset());
  }

  delete(receptionist: IReceptionist): void {
    const modalRef = this.modalService.open(ReceptionistDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.receptionist = receptionist;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateReceptionists(data: IReceptionist[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.receptionists.push(data[i]);
      }
    }
  }
}
