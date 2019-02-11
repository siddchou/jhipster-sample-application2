import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';

@Component({
    selector: 'jhi-user-address-map-ckw-detail',
    templateUrl: './user-address-map-ckw-detail.component.html'
})
export class UserAddressMapCkwDetailComponent implements OnInit {
    userAddressMap: IUserAddressMapCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userAddressMap }) => {
            this.userAddressMap = userAddressMap;
        });
    }

    previousState() {
        window.history.back();
    }
}
