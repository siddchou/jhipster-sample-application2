import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoryCkw } from 'app/shared/model/category-ckw.model';

type EntityResponseType = HttpResponse<ICategoryCkw>;
type EntityArrayResponseType = HttpResponse<ICategoryCkw[]>;

@Injectable({ providedIn: 'root' })
export class CategoryCkwService {
    public resourceUrl = SERVER_API_URL + 'api/categories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/categories';

    constructor(protected http: HttpClient) {}

    create(category: ICategoryCkw): Observable<EntityResponseType> {
        return this.http.post<ICategoryCkw>(this.resourceUrl, category, { observe: 'response' });
    }

    update(category: ICategoryCkw): Observable<EntityResponseType> {
        return this.http.put<ICategoryCkw>(this.resourceUrl, category, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoryCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoryCkw[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoryCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
