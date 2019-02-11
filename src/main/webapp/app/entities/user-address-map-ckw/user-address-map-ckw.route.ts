import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';
import { UserAddressMapCkwService } from './user-address-map-ckw.service';
import { UserAddressMapCkwComponent } from './user-address-map-ckw.component';
import { UserAddressMapCkwDetailComponent } from './user-address-map-ckw-detail.component';
import { UserAddressMapCkwUpdateComponent } from './user-address-map-ckw-update.component';
import { UserAddressMapCkwDeletePopupComponent } from './user-address-map-ckw-delete-dialog.component';
import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';

@Injectable({ providedIn: 'root' })
export class UserAddressMapCkwResolve implements Resolve<IUserAddressMapCkw> {
    constructor(private service: UserAddressMapCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserAddressMapCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserAddressMapCkw>) => response.ok),
                map((userAddressMap: HttpResponse<UserAddressMapCkw>) => userAddressMap.body)
            );
        }
        return of(new UserAddressMapCkw());
    }
}

export const userAddressMapRoute: Routes = [
    {
        path: '',
        component: UserAddressMapCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserAddressMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserAddressMapCkwDetailComponent,
        resolve: {
            userAddressMap: UserAddressMapCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddressMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserAddressMapCkwUpdateComponent,
        resolve: {
            userAddressMap: UserAddressMapCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddressMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserAddressMapCkwUpdateComponent,
        resolve: {
            userAddressMap: UserAddressMapCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddressMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAddressMapPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserAddressMapCkwDeletePopupComponent,
        resolve: {
            userAddressMap: UserAddressMapCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAddressMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
