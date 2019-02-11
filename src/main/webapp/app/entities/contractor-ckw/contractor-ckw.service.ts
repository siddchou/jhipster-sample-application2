import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractorCkw } from 'app/shared/model/contractor-ckw.model';

type EntityResponseType = HttpResponse<IContractorCkw>;
type EntityArrayResponseType = HttpResponse<IContractorCkw[]>;

@Injectable({ providedIn: 'root' })
export class ContractorCkwService {
    public resourceUrl = SERVER_API_URL + 'api/contractors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contractors';

    constructor(protected http: HttpClient) {}

    create(contractor: IContractorCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contractor);
        return this.http
            .post<IContractorCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contractor: IContractorCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contractor);
        return this.http
            .put<IContractorCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContractorCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContractorCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContractorCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(contractor: IContractorCkw): IContractorCkw {
        const copy: IContractorCkw = Object.assign({}, contractor, {
            hireDate: contractor.hireDate != null && contractor.hireDate.isValid() ? contractor.hireDate.toJSON() : null,
            startDate: contractor.startDate != null && contractor.startDate.isValid() ? contractor.startDate.toJSON() : null,
            endDate: contractor.endDate != null && contractor.endDate.isValid() ? contractor.endDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.hireDate = res.body.hireDate != null ? moment(res.body.hireDate) : null;
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((contractor: IContractorCkw) => {
                contractor.hireDate = contractor.hireDate != null ? moment(contractor.hireDate) : null;
                contractor.startDate = contractor.startDate != null ? moment(contractor.startDate) : null;
                contractor.endDate = contractor.endDate != null ? moment(contractor.endDate) : null;
            });
        }
        return res;
    }
}
