import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';

type EntityResponseType = HttpResponse<IUserAddressMapCkw>;
type EntityArrayResponseType = HttpResponse<IUserAddressMapCkw[]>;

@Injectable({ providedIn: 'root' })
export class UserAddressMapCkwService {
    public resourceUrl = SERVER_API_URL + 'api/user-address-maps';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/user-address-maps';

    constructor(protected http: HttpClient) {}

    create(userAddressMap: IUserAddressMapCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userAddressMap);
        return this.http
            .post<IUserAddressMapCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userAddressMap: IUserAddressMapCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userAddressMap);
        return this.http
            .put<IUserAddressMapCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserAddressMapCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserAddressMapCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserAddressMapCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(userAddressMap: IUserAddressMapCkw): IUserAddressMapCkw {
        const copy: IUserAddressMapCkw = Object.assign({}, userAddressMap, {
            startDate: userAddressMap.startDate != null && userAddressMap.startDate.isValid() ? userAddressMap.startDate.toJSON() : null,
            endDate: userAddressMap.endDate != null && userAddressMap.endDate.isValid() ? userAddressMap.endDate.toJSON() : null
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
            res.body.forEach((userAddressMap: IUserAddressMapCkw) => {
                userAddressMap.startDate = userAddressMap.startDate != null ? moment(userAddressMap.startDate) : null;
                userAddressMap.endDate = userAddressMap.endDate != null ? moment(userAddressMap.endDate) : null;
            });
        }
        return res;
    }
}
