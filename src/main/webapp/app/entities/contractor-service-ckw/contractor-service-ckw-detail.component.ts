import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';

@Component({
    selector: 'jhi-contractor-service-ckw-detail',
    templateUrl: './contractor-service-ckw-detail.component.html'
})
export class ContractorServiceCkwDetailComponent implements OnInit {
    contractorService: IContractorServiceCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contractorService }) => {
            this.contractorService = contractorService;
        });
    }

    previousState() {
        window.history.back();
    }
}
