import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    RateCkwComponent,
    RateCkwDetailComponent,
    RateCkwUpdateComponent,
    RateCkwDeletePopupComponent,
    RateCkwDeleteDialogComponent,
    rateRoute,
    ratePopupRoute
} from './';

const ENTITY_STATES = [...rateRoute, ...ratePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RateCkwComponent,
        RateCkwDetailComponent,
        RateCkwUpdateComponent,
        RateCkwDeleteDialogComponent,
        RateCkwDeletePopupComponent
    ],
    entryComponents: [RateCkwComponent, RateCkwUpdateComponent, RateCkwDeleteDialogComponent, RateCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2RateCkwModule {}
