import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppUserCkw } from 'app/shared/model/app-user-ckw.model';
import { AppUserCkwService } from './app-user-ckw.service';
import { AppUserCkwComponent } from './app-user-ckw.component';
import { AppUserCkwDetailComponent } from './app-user-ckw-detail.component';
import { AppUserCkwUpdateComponent } from './app-user-ckw-update.component';
import { AppUserCkwDeletePopupComponent } from './app-user-ckw-delete-dialog.component';
import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';

@Injectable({ providedIn: 'root' })
export class AppUserCkwResolve implements Resolve<IAppUserCkw> {
    constructor(private service: AppUserCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppUserCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AppUserCkw>) => response.ok),
                map((appUser: HttpResponse<AppUserCkw>) => appUser.body)
            );
        }
        return of(new AppUserCkw());
    }
}

export const appUserRoute: Routes = [
    {
        path: '',
        component: AppUserCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AppUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AppUserCkwDetailComponent,
        resolve: {
            appUser: AppUserCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AppUserCkwUpdateComponent,
        resolve: {
            appUser: AppUserCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AppUserCkwUpdateComponent,
        resolve: {
            appUser: AppUserCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appUserPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AppUserCkwDeletePopupComponent,
        resolve: {
            appUser: AppUserCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
