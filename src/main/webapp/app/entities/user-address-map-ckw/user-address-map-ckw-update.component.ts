import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';
import { UserAddressMapCkwService } from './user-address-map-ckw.service';
import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';
import { AppUserCkwService } from 'app/entities/app-user-ckw';
import { IAddressCkw } from 'app/shared/model/address-ckw.model';
import { AddressCkwService } from 'app/entities/address-ckw';

@Component({
    selector: 'jhi-user-address-map-ckw-update',
    templateUrl: './user-address-map-ckw-update.component.html'
})
export class UserAddressMapCkwUpdateComponent implements OnInit {
    userAddressMap: IUserAddressMapCkw;
    isSaving: boolean;

    appusers: IAppUserCkw[];

    addresses: IAddressCkw[];
    startDate: string;
    endDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userAddressMapService: UserAddressMapCkwService,
        protected appUserService: AppUserCkwService,
        protected addressService: AddressCkwService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userAddressMap }) => {
            this.userAddressMap = userAddressMap;
            this.startDate = this.userAddressMap.startDate != null ? this.userAddressMap.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.userAddressMap.endDate != null ? this.userAddressMap.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.appUserService
            .query({ filter: 'useraddressmap-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IAppUserCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAppUserCkw[]>) => response.body)
            )
            .subscribe(
                (res: IAppUserCkw[]) => {
                    if (!this.userAddressMap.appUserId) {
                        this.appusers = res;
                    } else {
                        this.appUserService
                            .find(this.userAddressMap.appUserId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IAppUserCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IAppUserCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IAppUserCkw) => (this.appusers = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.addressService
            .query({ filter: 'useraddressmap-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IAddressCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAddressCkw[]>) => response.body)
            )
            .subscribe(
                (res: IAddressCkw[]) => {
                    if (!this.userAddressMap.addressId) {
                        this.addresses = res;
                    } else {
                        this.addressService
                            .find(this.userAddressMap.addressId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IAddressCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IAddressCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IAddressCkw) => (this.addresses = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.userAddressMap.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.userAddressMap.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.userAddressMap.id !== undefined) {
            this.subscribeToSaveResponse(this.userAddressMapService.update(this.userAddressMap));
        } else {
            this.subscribeToSaveResponse(this.userAddressMapService.create(this.userAddressMap));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAddressMapCkw>>) {
        result.subscribe((res: HttpResponse<IUserAddressMapCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAddressById(index: number, item: IAddressCkw) {
        return item.id;
    }
}
