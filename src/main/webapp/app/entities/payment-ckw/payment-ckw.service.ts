import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentCkw } from 'app/shared/model/payment-ckw.model';

type EntityResponseType = HttpResponse<IPaymentCkw>;
type EntityArrayResponseType = HttpResponse<IPaymentCkw[]>;

@Injectable({ providedIn: 'root' })
export class PaymentCkwService {
    public resourceUrl = SERVER_API_URL + 'api/payments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/payments';

    constructor(protected http: HttpClient) {}

    create(payment: IPaymentCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(payment);
        return this.http
            .post<IPaymentCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(payment: IPaymentCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(payment);
        return this.http
            .put<IPaymentCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPaymentCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPaymentCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPaymentCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(payment: IPaymentCkw): IPaymentCkw {
        const copy: IPaymentCkw = Object.assign({}, payment, {
            startDate: payment.startDate != null && payment.startDate.isValid() ? payment.startDate.toJSON() : null,
            endDate: payment.endDate != null && payment.endDate.isValid() ? payment.endDate.toJSON() : null
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
            res.body.forEach((payment: IPaymentCkw) => {
                payment.startDate = payment.startDate != null ? moment(payment.startDate) : null;
                payment.endDate = payment.endDate != null ? moment(payment.endDate) : null;
            });
        }
        return res;
    }
}
