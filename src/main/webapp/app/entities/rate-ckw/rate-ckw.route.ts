import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RateCkw } from 'app/shared/model/rate-ckw.model';
import { RateCkwService } from './rate-ckw.service';
import { RateCkwComponent } from './rate-ckw.component';
import { RateCkwDetailComponent } from './rate-ckw-detail.component';
import { RateCkwUpdateComponent } from './rate-ckw-update.component';
import { RateCkwDeletePopupComponent } from './rate-ckw-delete-dialog.component';
import { IRateCkw } from 'app/shared/model/rate-ckw.model';

@Injectable({ providedIn: 'root' })
export class RateCkwResolve implements Resolve<IRateCkw> {
    constructor(private service: RateCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRateCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RateCkw>) => response.ok),
                map((rate: HttpResponse<RateCkw>) => rate.body)
            );
        }
        return of(new RateCkw());
    }
}

export const rateRoute: Routes = [
    {
        path: '',
        component: RateCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Rates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RateCkwDetailComponent,
        resolve: {
            rate: RateCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RateCkwUpdateComponent,
        resolve: {
            rate: RateCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RateCkwUpdateComponent,
        resolve: {
            rate: RateCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ratePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RateCkwDeletePopupComponent,
        resolve: {
            rate: RateCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
