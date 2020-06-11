import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReceptionist, Receptionist } from 'app/shared/model/receptionist.model';
import { ReceptionistService } from './receptionist.service';
import { ReceptionistComponent } from './receptionist.component';
import { ReceptionistDetailComponent } from './receptionist-detail.component';
import { ReceptionistUpdateComponent } from './receptionist-update.component';

@Injectable({ providedIn: 'root' })
export class ReceptionistResolve implements Resolve<IReceptionist> {
  constructor(private service: ReceptionistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceptionist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((receptionist: HttpResponse<Receptionist>) => {
          if (receptionist.body) {
            return of(receptionist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Receptionist());
  }
}

export const receptionistRoute: Routes = [
  {
    path: '',
    component: ReceptionistComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Receptionists'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReceptionistDetailComponent,
    resolve: {
      receptionist: ReceptionistResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Receptionists'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReceptionistUpdateComponent,
    resolve: {
      receptionist: ReceptionistResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Receptionists'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReceptionistUpdateComponent,
    resolve: {
      receptionist: ReceptionistResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Receptionists'
    },
    canActivate: [UserRouteAccessService]
  }
];
