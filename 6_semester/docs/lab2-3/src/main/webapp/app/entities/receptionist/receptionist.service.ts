import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReceptionist } from 'app/shared/model/receptionist.model';

type EntityResponseType = HttpResponse<IReceptionist>;
type EntityArrayResponseType = HttpResponse<IReceptionist[]>;

@Injectable({ providedIn: 'root' })
export class ReceptionistService {
  public resourceUrl = SERVER_API_URL + 'api/receptionists';

  constructor(protected http: HttpClient) {}

  create(receptionist: IReceptionist): Observable<EntityResponseType> {
    return this.http.post<IReceptionist>(this.resourceUrl, receptionist, { observe: 'response' });
  }

  update(receptionist: IReceptionist): Observable<EntityResponseType> {
    return this.http.put<IReceptionist>(this.resourceUrl, receptionist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReceptionist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReceptionist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
