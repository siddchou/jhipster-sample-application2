import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentCkw } from 'app/shared/model/payment-ckw.model';

@Component({
    selector: 'jhi-payment-ckw-detail',
    templateUrl: './payment-ckw-detail.component.html'
})
export class PaymentCkwDetailComponent implements OnInit {
    payment: IPaymentCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payment }) => {
            this.payment = payment;
        });
    }

    previousState() {
        window.history.back();
    }
}
