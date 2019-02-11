import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocationCkw } from 'app/shared/model/location-ckw.model';

type EntityResponseType = HttpResponse<ILocationCkw>;
type EntityArrayResponseType = HttpResponse<ILocationCkw[]>;

@Injectable({ providedIn: 'root' })
export class LocationCkwService {
    public resourceUrl = SERVER_API_URL + 'api/locations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/locations';

    constructor(protected http: HttpClient) {}

    create(location: ILocationCkw): Observable<EntityResponseType> {
        return this.http.post<ILocationCkw>(this.resourceUrl, location, { observe: 'response' });
    }

    update(location: ILocationCkw): Observable<EntityResponseType> {
        return this.http.put<ILocationCkw>(this.resourceUrl, location, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILocationCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocationCkw[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocationCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
