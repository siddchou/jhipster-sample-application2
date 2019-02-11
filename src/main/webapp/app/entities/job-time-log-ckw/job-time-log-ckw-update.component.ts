import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IJobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';
import { JobTimeLogCkwService } from './job-time-log-ckw.service';
import { IJobHistoryCkw } from 'app/shared/model/job-history-ckw.model';
import { JobHistoryCkwService } from 'app/entities/job-history-ckw';

@Component({
    selector: 'jhi-job-time-log-ckw-update',
    templateUrl: './job-time-log-ckw-update.component.html'
})
export class JobTimeLogCkwUpdateComponent implements OnInit {
    jobTimeLog: IJobTimeLogCkw;
    isSaving: boolean;

    jobhistories: IJobHistoryCkw[];
    startDate: string;
    endDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected jobTimeLogService: JobTimeLogCkwService,
        protected jobHistoryService: JobHistoryCkwService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobTimeLog }) => {
            this.jobTimeLog = jobTimeLog;
            this.startDate = this.jobTimeLog.startDate != null ? this.jobTimeLog.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.jobTimeLog.endDate != null ? this.jobTimeLog.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.jobHistoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJobHistoryCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJobHistoryCkw[]>) => response.body)
            )
            .subscribe((res: IJobHistoryCkw[]) => (this.jobhistories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.jobTimeLog.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.jobTimeLog.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.jobTimeLog.id !== undefined) {
            this.subscribeToSaveResponse(this.jobTimeLogService.update(this.jobTimeLog));
        } else {
            this.subscribeToSaveResponse(this.jobTimeLogService.create(this.jobTimeLog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobTimeLogCkw>>) {
        result.subscribe((res: HttpResponse<IJobTimeLogCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackJobHistoryById(index: number, item: IJobHistoryCkw) {
        return item.id;
    }
}
