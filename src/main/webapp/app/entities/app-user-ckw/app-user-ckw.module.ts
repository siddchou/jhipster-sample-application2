import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    AppUserCkwComponent,
    AppUserCkwDetailComponent,
    AppUserCkwUpdateComponent,
    AppUserCkwDeletePopupComponent,
    AppUserCkwDeleteDialogComponent,
    appUserRoute,
    appUserPopupRoute
} from './';

const ENTITY_STATES = [...appUserRoute, ...appUserPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AppUserCkwComponent,
        AppUserCkwDetailComponent,
        AppUserCkwUpdateComponent,
        AppUserCkwDeleteDialogComponent,
        AppUserCkwDeletePopupComponent
    ],
    entryComponents: [AppUserCkwComponent, AppUserCkwUpdateComponent, AppUserCkwDeleteDialogComponent, AppUserCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2AppUserCkwModule {}
