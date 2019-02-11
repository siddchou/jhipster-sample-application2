import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServicesCkw } from 'app/shared/model/services-ckw.model';
import { ServicesCkwService } from './services-ckw.service';
import { IRateCkw } from 'app/shared/model/rate-ckw.model';
import { RateCkwService } from 'app/entities/rate-ckw';
import { ICategoryCkw } from 'app/shared/model/category-ckw.model';
import { CategoryCkwService } from 'app/entities/category-ckw';
import { ILocationCkw } from 'app/shared/model/location-ckw.model';
import { LocationCkwService } from 'app/entities/location-ckw';

@Component({
    selector: 'jhi-services-ckw-update',
    templateUrl: './services-ckw-update.component.html'
})
export class ServicesCkwUpdateComponent implements OnInit {
    services: IServicesCkw;
    isSaving: boolean;

    rates: IRateCkw[];

    categories: ICategoryCkw[];

    locations: ILocationCkw[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected servicesService: ServicesCkwService,
        protected rateService: RateCkwService,
        protected categoryService: CategoryCkwService,
        protected locationService: LocationCkwService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ services }) => {
            this.services = services;
        });
        this.rateService
            .query({ filter: 'services-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IRateCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRateCkw[]>) => response.body)
            )
            .subscribe(
                (res: IRateCkw[]) => {
                    if (!this.services.rateId) {
                        this.rates = res;
                    } else {
                        this.rateService
                            .find(this.services.rateId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IRateCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IRateCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IRateCkw) => (this.rates = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.categoryService
            .query({ filter: 'services-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICategoryCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategoryCkw[]>) => response.body)
            )
            .subscribe(
                (res: ICategoryCkw[]) => {
                    if (!this.services.categoryId) {
                        this.categories = res;
                    } else {
                        this.categoryService
                            .find(this.services.categoryId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICategoryCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICategoryCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICategoryCkw) => (this.categories = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.locationService
            .query({ filter: 'services-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILocationCkw[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILocationCkw[]>) => response.body)
            )
            .subscribe(
                (res: ILocationCkw[]) => {
                    if (!this.services.locationId) {
                        this.locations = res;
                    } else {
                        this.locationService
                            .find(this.services.locationId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ILocationCkw>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ILocationCkw>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ILocationCkw) => (this.locations = [subRes].concat(res)),
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
        if (this.services.id !== undefined) {
            this.subscribeToSaveResponse(this.servicesService.update(this.services));
        } else {
            this.subscribeToSaveResponse(this.servicesService.create(this.services));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicesCkw>>) {
        result.subscribe((res: HttpResponse<IServicesCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRateById(index: number, item: IRateCkw) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategoryCkw) {
        return item.id;
    }

    trackLocationById(index: number, item: ILocationCkw) {
        return item.id;
    }
}
