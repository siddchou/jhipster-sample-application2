import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';
import { ContractorServiceCkwService } from './contractor-service-ckw.service';
import { ContractorServiceCkwComponent } from './contractor-service-ckw.component';
import { ContractorServiceCkwDetailComponent } from './contractor-service-ckw-detail.component';
import { ContractorServiceCkwUpdateComponent } from './contractor-service-ckw-update.component';
import { ContractorServiceCkwDeletePopupComponent } from './contractor-service-ckw-delete-dialog.component';
import { IContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';

@Injectable({ providedIn: 'root' })
export class ContractorServiceCkwResolve implements Resolve<IContractorServiceCkw> {
    constructor(private service: ContractorServiceCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractorServiceCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContractorServiceCkw>) => response.ok),
                map((contractorService: HttpResponse<ContractorServiceCkw>) => contractorService.body)
            );
        }
        return of(new ContractorServiceCkw());
    }
}

export const contractorServiceRoute: Routes = [
    {
        path: '',
        component: ContractorServiceCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ContractorServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ContractorServiceCkwDetailComponent,
        resolve: {
            contractorService: ContractorServiceCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContractorServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContractorServiceCkwUpdateComponent,
        resolve: {
            contractorService: ContractorServiceCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContractorServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContractorServiceCkwUpdateComponent,
        resolve: {
            contractorService: ContractorServiceCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContractorServices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contractorServicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContractorServiceCkwDeletePopupComponent,
        resolve: {
            contractorService: ContractorServiceCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContractorServices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
