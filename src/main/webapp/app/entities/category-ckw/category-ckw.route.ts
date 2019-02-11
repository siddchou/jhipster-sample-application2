import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CategoryCkw } from 'app/shared/model/category-ckw.model';
import { CategoryCkwService } from './category-ckw.service';
import { CategoryCkwComponent } from './category-ckw.component';
import { CategoryCkwDetailComponent } from './category-ckw-detail.component';
import { CategoryCkwUpdateComponent } from './category-ckw-update.component';
import { CategoryCkwDeletePopupComponent } from './category-ckw-delete-dialog.component';
import { ICategoryCkw } from 'app/shared/model/category-ckw.model';

@Injectable({ providedIn: 'root' })
export class CategoryCkwResolve implements Resolve<ICategoryCkw> {
    constructor(private service: CategoryCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategoryCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CategoryCkw>) => response.ok),
                map((category: HttpResponse<CategoryCkw>) => category.body)
            );
        }
        return of(new CategoryCkw());
    }
}

export const categoryRoute: Routes = [
    {
        path: '',
        component: CategoryCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CategoryCkwDetailComponent,
        resolve: {
            category: CategoryCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CategoryCkwUpdateComponent,
        resolve: {
            category: CategoryCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CategoryCkwUpdateComponent,
        resolve: {
            category: CategoryCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CategoryCkwDeletePopupComponent,
        resolve: {
            category: CategoryCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Categories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
