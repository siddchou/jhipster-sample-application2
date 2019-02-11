import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

type EntityResponseType = HttpResponse<IJobTimeLogCkw>;
type EntityArrayResponseType = HttpResponse<IJobTimeLogCkw[]>;

@Injectable({ providedIn: 'root' })
export class JobTimeLogCkwService {
    public resourceUrl = SERVER_API_URL + 'api/job-time-logs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/job-time-logs';

    constructor(protected http: HttpClient) {}

    create(jobTimeLog: IJobTimeLogCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobTimeLog);
        return this.http
            .post<IJobTimeLogCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(jobTimeLog: IJobTimeLogCkw): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobTimeLog);
        return this.http
            .put<IJobTimeLogCkw>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IJobTimeLogCkw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJobTimeLogCkw[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJobTimeLogCkw[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(jobTimeLog: IJobTimeLogCkw): IJobTimeLogCkw {
        const copy: IJobTimeLogCkw = Object.assign({}, jobTimeLog, {
            startDate: jobTimeLog.startDate != null && jobTimeLog.startDate.isValid() ? jobTimeLog.startDate.toJSON() : null,
            endDate: jobTimeLog.endDate != null && jobTimeLog.endDate.isValid() ? jobTimeLog.endDate.toJSON() : null
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
            res.body.forEach((jobTimeLog: IJobTimeLogCkw) => {
                jobTimeLog.startDate = jobTimeLog.startDate != null ? moment(jobTimeLog.startDate) : null;
                jobTimeLog.endDate = jobTimeLog.endDate != null ? moment(jobTimeLog.endDate) : null;
            });
        }
        return res;
    }
}
