import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IJobHistoryCkw } from 'app/shared/model/job-history-ckw.model';
import { JobHistoryCkwService } from './job-history-ckw.service';
import { IPaymentCkw } from 'app/shared/model/payment-ckw.model';
import { PaymentCkwService } from 'app/entities/payment-ckw';
import { IContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';
import { ContractorServiceCkwService } from 'app/entities/contractor-service-ckw';
import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';
import { UserAddressMapCkwService } from 'app/entities/user-address-map-ckw';

@Component({
    selector: 'jhi-job-history-ckw-update',
    templateUrl: './job-history-ckw-update.component.html'
})
export class JobHistoryCkwUpdateComponent implements OnInit {
    jobHistory: IJobHistoryCkw;
    isSaving: boolean;

    payments: IPaymentCkw[];

    contractorservices: IContractorServiceCkw[];

    useraddressmaps: IUserAddressMapCkw[];
    startDate: string;
    endDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected jobHistoryService: JobHistoryCkwService,
        protected paymentService: PaymentCkwService,
        protected contractorServiceService: ContractorServiceCkwService,
        protected userAddressMapService: UserAddressMapCkwService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobHistory }) => {
            this.jobHistory = jobHistory;
            this.startDate = this.jobHistory.startDate != null ? this.jobHistory.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.jobHistory.endDate != null ? this.jobHistory.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.paymentService
            .query({ 'jobHistoryId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPaymentCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPaymentCkw[]>) => response.body)
            )
            .subscribe(
                (res: IPaymentCkw[]) => {
                    if (!this.jobHistory.paymentId) {
                        this.payments = res;
                    } else {
                        this.paymentService
                            .find(this.jobHistory.paymentId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPaymentCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPaymentCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPaymentCkw) => (this.payments = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.contractorServiceService
            .query({ 'jobHistoryId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IContractorServiceCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContractorServiceCkw[]>) => response.body)
            )
            .subscribe(
                (res: IContractorServiceCkw[]) => {
                    if (!this.jobHistory.contractorServiceId) {
                        this.contractorservices = res;
                    } else {
                        this.contractorServiceService
                            .find(this.jobHistory.contractorServiceId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IContractorServiceCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IContractorServiceCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IContractorServiceCkw) => (this.contractorservices = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.userAddressMapService
            .query({ 'jobHistoryId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUserAddressMapCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserAddressMapCkw[]>) => response.body)
            )
            .subscribe(
                (res: IUserAddressMapCkw[]) => {
                    if (!this.jobHistory.userAddressMapId) {
                        this.useraddressmaps = res;
                    } else {
                        this.userAddressMapService
                            .find(this.jobHistory.userAddressMapId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUserAddressMapCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUserAddressMapCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUserAddressMapCkw) => (this.useraddressmaps = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.jobHistory.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.jobHistory.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.jobHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.jobHistoryService.update(this.jobHistory));
        } else {
            this.subscribeToSaveResponse(this.jobHistoryService.create(this.jobHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistoryCkw>>) {
        result.subscribe((res: HttpResponse<IJobHistoryCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPaymentById(index: number, item: IPaymentCkw) {
        return item.id;
    }

    trackContractorServiceById(index: number, item: IContractorServiceCkw) {
        return item.id;
    }

    trackUserAddressMapById(index: number, item: IUserAddressMapCkw) {
        return item.id;
    }
}
