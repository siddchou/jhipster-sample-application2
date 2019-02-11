import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRateCkw } from 'app/shared/model/rate-ckw.model';

type EntityResponseType = HttpResponse<IRateCkw>;
type EntityArrayResponseType = HttpResponse<IRateCkw[]>;

@Injectable({ providedIn: 'root' })
export class RateCkwService {
    public resourceUrl = SERVER_API_URL + 'api/rates';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/rates';

    constructor(protected http: HttpClient) {}

    create(rate: IRateCkw): Observable<EntityResponseType> {
        return this.http.post<IRateCkw>(this.resourceUrl, rate, { observe: 'response' });
    }

    update(rate: IRateCkw): Observable<EntityResponseType> {
        return this.http.put<IRateCkw>(this.resourceUrl, rate, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRateCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRateCkw[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRateCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
