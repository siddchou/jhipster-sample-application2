import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILocationCkw } from 'app/shared/model/location-ckw.model';
import { LocationCkwService } from './location-ckw.service';

@Component({
    selector: 'jhi-location-ckw-update',
    templateUrl: './location-ckw-update.component.html'
})
export class LocationCkwUpdateComponent implements OnInit {
    location: ILocationCkw;
    isSaving: boolean;

    constructor(protected locationService: LocationCkwService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ location }) => {
            this.location = location;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(this.locationService.create(this.location));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationCkw>>) {
        result.subscribe((res: HttpResponse<ILocationCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
