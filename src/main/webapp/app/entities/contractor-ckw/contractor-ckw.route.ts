import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractorCkw } from 'app/shared/model/contractor-ckw.model';
import { ContractorCkwService } from './contractor-ckw.service';
import { ContractorCkwComponent } from './contractor-ckw.component';
import { ContractorCkwDetailComponent } from './contractor-ckw-detail.component';
import { ContractorCkwUpdateComponent } from './contractor-ckw-update.component';
import { ContractorCkwDeletePopupComponent } from './contractor-ckw-delete-dialog.component';
import { IContractorCkw } from 'app/shared/model/contractor-ckw.model';

@Injectable({ providedIn: 'root' })
export class ContractorCkwResolve implements Resolve<IContractorCkw> {
    constructor(private service: ContractorCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractorCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContractorCkw>) => response.ok),
                map((contractor: HttpResponse<ContractorCkw>) => contractor.body)
            );
        }
        return of(new ContractorCkw());
    }
}

export const contractorRoute: Routes = [
    {
        path: '',
        component: ContractorCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Contractors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ContractorCkwDetailComponent,
        resolve: {
            contractor: ContractorCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contractors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContractorCkwUpdateComponent,
        resolve: {
            contractor: ContractorCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contractors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContractorCkwUpdateComponent,
        resolve: {
            contractor: ContractorCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contractors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contractorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContractorCkwDeletePopupComponent,
        resolve: {
            contractor: ContractorCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contractors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
