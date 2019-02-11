import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';

type EntityResponseType = HttpResponse<IAppUserCkw>;
type EntityArrayResponseType = HttpResponse<IAppUserCkw[]>;

@Injectable({ providedIn: 'root' })
export class AppUserCkwService {
    public resourceUrl = SERVER_API_URL + 'api/app-users';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/app-users';

    constructor(protected http: HttpClient) {}

    create(appUser: IAppUserCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appUser);
        return this.http
            .post<IAppUserCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(appUser: IAppUserCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appUser);
        return this.http
            .put<IAppUserCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAppUserCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAppUserCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAppUserCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(appUser: IAppUserCkw): IAppUserCkw {
        const copy: IAppUserCkw = Object.assign({}, appUser, {
            startDate: appUser.startDate != null && appUser.startDate.isValid() ? appUser.startDate.toJSON() : null,
            endDate: appUser.endDate != null && appUser.endDate.isValid() ? appUser.endDate.toJSON() : null
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
            res.body.forEach((appUser: IAppUserCkw) => {
                appUser.startDate = appUser.startDate != null ? moment(appUser.startDate) : null;
                appUser.endDate = appUser.endDate != null ? moment(appUser.endDate) : null;
            });
        }
        return res;
    }
}
