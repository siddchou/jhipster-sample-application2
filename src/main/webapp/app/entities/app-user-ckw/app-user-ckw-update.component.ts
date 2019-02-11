import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';
import { AppUserCkwService } from './app-user-ckw.service';

@Component({
    selector: 'jhi-app-user-ckw-update',
    templateUrl: './app-user-ckw-update.component.html'
})
export class AppUserCkwUpdateComponent implements OnInit {
    appUser: IAppUserCkw;
    isSaving: boolean;
    startDate: string;
    endDate: string;

    constructor(protected appUserService: AppUserCkwService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appUser }) => {
            this.appUser = appUser;
            this.startDate = this.appUser.startDate != null ? this.appUser.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.appUser.endDate != null ? this.appUser.endDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.appUser.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.appUser.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.appUser.id !== undefined) {
            this.subscribeToSaveResponse(this.appUserService.update(this.appUser));
        } else {
            this.subscribeToSaveResponse(this.appUserService.create(this.appUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppUserCkw>>) {
        result.subscribe((res: HttpResponse<IAppUserCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
