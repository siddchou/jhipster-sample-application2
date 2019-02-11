import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    JobTimeLogCkwComponent,
    JobTimeLogCkwDetailComponent,
    JobTimeLogCkwUpdateComponent,
    JobTimeLogCkwDeletePopupComponent,
    JobTimeLogCkwDeleteDialogComponent,
    jobTimeLogRoute,
    jobTimeLogPopupRoute
} from './';

const ENTITY_STATES = [...jobTimeLogRoute, ...jobTimeLogPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JobTimeLogCkwComponent,
        JobTimeLogCkwDetailComponent,
        JobTimeLogCkwUpdateComponent,
        JobTimeLogCkwDeleteDialogComponent,
        JobTimeLogCkwDeletePopupComponent
    ],
    entryComponents: [
        JobTimeLogCkwComponent,
        JobTimeLogCkwUpdateComponent,
        JobTimeLogCkwDeleteDialogComponent,
        JobTimeLogCkwDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2JobTimeLogCkwModule {}
