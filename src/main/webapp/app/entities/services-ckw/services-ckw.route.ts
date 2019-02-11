import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServicesCkw } from 'app/shared/model/services-ckw.model';
import { ServicesCkwService } from './services-ckw.service';
import { ServicesCkwComponent } from './services-ckw.component';
import { ServicesCkwDetailComponent } from './services-ckw-detail.component';
import { ServicesCkwUpdateComponent } from './services-ckw-update.component';
import { ServicesCkwDeletePopupComponent } from './services-ckw-delete-dialog.component';
import { IServicesCkw } from 'app/shared/model/services-ckw.model';

@Injectable({ providedIn: 'root' })
export class ServicesCkwResolve implements Resolve<IServicesCkw> {
    constructor(private service: ServicesCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServicesCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ServicesCkw>) => response.ok),
                map((services: HttpResponse<ServicesCkw>) => services.body)
            );
        }
        return of(new ServicesCkw());
    }
}

export const servicesRoute: Routes = [
    {
        path: '',
        component: ServicesCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Services'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ServicesCkwDetailComponent,
        resolve: {
            services: ServicesCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Services'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ServicesCkwUpdateComponent,
        resolve: {
            services: ServicesCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Services'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ServicesCkwUpdateComponent,
        resolve: {
            services: ServicesCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Services'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ServicesCkwDeletePopupComponent,
        resolve: {
            services: ServicesCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Services'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
