import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPaymentCkw } from 'app/shared/model/payment-ckw.model';
import { PaymentCkwService } from './payment-ckw.service';

@Component({
    selector: 'jhi-payment-ckw-update',
    templateUrl: './payment-ckw-update.component.html'
})
export class PaymentCkwUpdateComponent implements OnInit {
    payment: IPaymentCkw;
    isSaving: boolean;
    startDate: string;
    endDate: string;

    constructor(protected paymentService: PaymentCkwService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ payment }) => {
            this.payment = payment;
            this.startDate = this.payment.startDate != null ? this.payment.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.payment.endDate != null ? this.payment.endDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.payment.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.payment.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.payment.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentService.update(this.payment));
        } else {
            this.subscribeToSaveResponse(this.paymentService.create(this.payment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentCkw>>) {
        result.subscribe((res: HttpResponse<IPaymentCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
