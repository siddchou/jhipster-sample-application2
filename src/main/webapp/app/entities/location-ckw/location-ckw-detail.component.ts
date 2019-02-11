import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationCkw } from 'app/shared/model/location-ckw.model';

@Component({
    selector: 'jhi-location-ckw-detail',
    templateUrl: './location-ckw-detail.component.html'
})
export class LocationCkwDetailComponent implements OnInit {
    location: ILocationCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ location }) => {
            this.location = location;
        });
    }

    previousState() {
        window.history.back();
    }
}
