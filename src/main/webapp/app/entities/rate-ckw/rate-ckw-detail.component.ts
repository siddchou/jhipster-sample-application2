import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRateCkw } from 'app/shared/model/rate-ckw.model';

@Component({
    selector: 'jhi-rate-ckw-detail',
    templateUrl: './rate-ckw-detail.component.html'
})
export class RateCkwDetailComponent implements OnInit {
    rate: IRateCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rate }) => {
            this.rate = rate;
        });
    }

    previousState() {
        window.history.back();
    }
}
