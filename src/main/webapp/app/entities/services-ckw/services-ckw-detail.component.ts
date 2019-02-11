import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServicesCkw } from 'app/shared/model/services-ckw.model';

@Component({
    selector: 'jhi-services-ckw-detail',
    templateUrl: './services-ckw-detail.component.html'
})
export class ServicesCkwDetailComponent implements OnInit {
    services: IServicesCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ services }) => {
            this.services = services;
        });
    }

    previousState() {
        window.history.back();
    }
}
