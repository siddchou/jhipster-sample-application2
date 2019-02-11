import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AddressCkw } from 'app/shared/model/address-ckw.model';
import { AddressCkwService } from './address-ckw.service';
import { AddressCkwComponent } from './address-ckw.component';
import { AddressCkwDetailComponent } from './address-ckw-detail.component';
import { AddressCkwUpdateComponent } from './address-ckw-update.component';
import { AddressCkwDeletePopupComponent } from './address-ckw-delete-dialog.component';
import { IAddressCkw } from 'app/shared/model/address-ckw.model';

@Injectable({ providedIn: 'root' })
export class AddressCkwResolve implements Resolve<IAddressCkw> {
    constructor(private service: AddressCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAddressCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AddressCkw>) => response.ok),
                map((address: HttpResponse<AddressCkw>) => address.body)
            );
        }
        return of(new AddressCkw());
    }
}

export const addressRoute: Routes = [
    {
        path: '',
        component: AddressCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Addresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AddressCkwDetailComponent,
        resolve: {
            address: AddressCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AddressCkwUpdateComponent,
        resolve: {
            address: AddressCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AddressCkwUpdateComponent,
        resolve: {
            address: AddressCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AddressCkwDeletePopupComponent,
        resolve: {
            address: AddressCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
