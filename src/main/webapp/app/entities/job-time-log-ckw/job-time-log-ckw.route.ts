import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';
import { JobTimeLogCkwService } from './job-time-log-ckw.service';
import { JobTimeLogCkwComponent } from './job-time-log-ckw.component';
import { JobTimeLogCkwDetailComponent } from './job-time-log-ckw-detail.component';
import { JobTimeLogCkwUpdateComponent } from './job-time-log-ckw-update.component';
import { JobTimeLogCkwDeletePopupComponent } from './job-time-log-ckw-delete-dialog.component';
import { IJobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

@Injectable({ providedIn: 'root' })
export class JobTimeLogCkwResolve implements Resolve<IJobTimeLogCkw> {
    constructor(private service: JobTimeLogCkwService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobTimeLogCkw> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobTimeLogCkw>) => response.ok),
                map((jobTimeLog: HttpResponse<JobTimeLogCkw>) => jobTimeLog.body)
            );
        }
        return of(new JobTimeLogCkw());
    }
}

export const jobTimeLogRoute: Routes = [
    {
        path: '',
        component: JobTimeLogCkwComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'JobTimeLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JobTimeLogCkwDetailComponent,
        resolve: {
            jobTimeLog: JobTimeLogCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobTimeLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JobTimeLogCkwUpdateComponent,
        resolve: {
            jobTimeLog: JobTimeLogCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobTimeLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JobTimeLogCkwUpdateComponent,
        resolve: {
            jobTimeLog: JobTimeLogCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobTimeLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobTimeLogPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JobTimeLogCkwDeletePopupComponent,
        resolve: {
            jobTimeLog: JobTimeLogCkwResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobTimeLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
