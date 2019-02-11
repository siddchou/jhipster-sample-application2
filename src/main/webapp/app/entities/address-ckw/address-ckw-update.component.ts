import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAddressCkw } from 'app/shared/model/address-ckw.model';
import { AddressCkwService } from './address-ckw.service';
import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';
import { AppUserCkwService } from 'app/entities/app-user-ckw';

@Component({
    selector: 'jhi-address-ckw-update',
    templateUrl: './address-ckw-update.component.html'
})
export class AddressCkwUpdateComponent implements OnInit {
    address: IAddressCkw;
    isSaving: boolean;

    appusers: IAppUserCkw[];
    startDate: string;
    endDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected addressService: AddressCkwService,
        protected appUserService: AppUserCkwService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ address }) => {
            this.address = address;
            this.startDate = this.address.startDate != null ? this.address.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.address.endDate != null ? this.address.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.appUserService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAppUserCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAppUserCkw[]>) => response.body)
            )
            .subscribe((res: IAppUserCkw[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.address.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.address.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.address.id !== undefined) {
            this.subscribeToSaveResponse(this.addressService.update(this.address));
        } else {
            this.subscribeToSaveResponse(this.addressService.create(this.address));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressCkw>>) {
        result.subscribe((res: HttpResponse<IAddressCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAppUserById(index: number, item: IAppUserCkw) {
        return item.id;
    }
}
