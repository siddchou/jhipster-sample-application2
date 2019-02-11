import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractorCkw } from 'app/shared/model/contractor-ckw.model';

@Component({
    selector: 'jhi-contractor-ckw-detail',
    templateUrl: './contractor-ckw-detail.component.html'
})
export class ContractorCkwDetailComponent implements OnInit {
    contractor: IContractorCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contractor }) => {
            this.contractor = contractor;
        });
    }

    previousState() {
        window.history.back();
    }
}
