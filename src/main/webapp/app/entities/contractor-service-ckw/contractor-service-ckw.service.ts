import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';

type EntityResponseType = HttpResponse<IContractorServiceCkw>;
type EntityArrayResponseType = HttpResponse<IContractorServiceCkw[]>;

@Injectable({ providedIn: 'root' })
export class ContractorServiceCkwService {
    public resourceUrl = SERVER_API_URL + 'api/contractor-services';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contractor-services';

    constructor(protected http: HttpClient) {}

    create(contractorService: IContractorServiceCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contractorService);
        return this.http
            .post<IContractorServiceCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contractorService: IContractorServiceCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contractorService);
        return this.http
            .put<IContractorServiceCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContractorServiceCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContractorServiceCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContractorServiceCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(contractorService: IContractorServiceCkw): IContractorServiceCkw {
        const copy: IContractorServiceCkw = Object.assign({}, contractorService, {
            startDate:
                contractorService.startDate != null && contractorService.startDate.isValid() ? contractorService.startDate.toJSON() : null,
            endDate: contractorService.endDate != null && contractorService.endDate.isValid() ? contractorService.endDate.toJSON() : null
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
            res.body.forEach((contractorService: IContractorServiceCkw) => {
                contractorService.startDate = contractorService.startDate != null ? moment(contractorService.startDate) : null;
                contractorService.endDate = contractorService.endDate != null ? moment(contractorService.endDate) : null;
            });
        }
        return res;
    }
}
