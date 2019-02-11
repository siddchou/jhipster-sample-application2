import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAddressCkw } from 'app/shared/model/address-ckw.model';

type EntityResponseType = HttpResponse<IAddressCkw>;
type EntityArrayResponseType = HttpResponse<IAddressCkw[]>;

@Injectable({ providedIn: 'root' })
export class AddressCkwService {
    public resourceUrl = SERVER_API_URL + 'api/addresses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/addresses';

    constructor(protected http: HttpClient) {}

    create(address: IAddressCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(address);
        return this.http
            .post<IAddressCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(address: IAddressCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(address);
        return this.http
            .put<IAddressCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAddressCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAddressCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAddressCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(address: IAddressCkw): IAddressCkw {
        const copy: IAddressCkw = Object.assign({}, address, {
            startDate: address.startDate != null && address.startDate.isValid() ? address.startDate.toJSON() : null,
            endDate: address.endDate != null && address.endDate.isValid() ? address.endDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((address: IAddressCkw) => {
                address.startDate = address.startDate != null ? moment(address.startDate) : null;
                address.endDate = address.endDate != null ? moment(address.endDate) : null;
            });
        }
        return res;
    }
}
