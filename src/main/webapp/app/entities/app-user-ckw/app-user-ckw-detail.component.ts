import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';

@Component({
    selector: 'jhi-app-user-ckw-detail',
    templateUrl: './app-user-ckw-detail.component.html'
})
export class AppUserCkwDetailComponent implements OnInit {
    appUser: IAppUserCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appUser }) => {
            this.appUser = appUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
