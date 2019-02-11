import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressCkw } from 'app/shared/model/address-ckw.model';

@Component({
    selector: 'jhi-address-ckw-detail',
    templateUrl: './address-ckw-detail.component.html'
})
export class AddressCkwDetailComponent implements OnInit {
    address: IAddressCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ address }) => {
            this.address = address;
        });
    }

    previousState() {
        window.history.back();
    }
}
