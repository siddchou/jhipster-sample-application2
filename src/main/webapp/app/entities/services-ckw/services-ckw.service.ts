import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServicesCkw } from 'app/shared/model/services-ckw.model';

type EntityResponseType = HttpResponse<IServicesCkw>;
type EntityArrayResponseType = HttpResponse<IServicesCkw[]>;

@Injectable({ providedIn: 'root' })
export class ServicesCkwService {
    public resourceUrl = SERVER_API_URL + 'api/services';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/services';

    constructor(protected http: HttpClient) {}

    create(services: IServicesCkw): Observable<EntityResponseType> {
        return this.http.post<IServicesCkw>(this.resourceUrl, services, { observe: 'response' });
    }

    update(services: IServicesCkw): Observable<EntityResponseType> {
        return this.http.put<IServicesCkw>(this.resourceUrl, services, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IServicesCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServicesCkw[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServicesCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
