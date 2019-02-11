import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    LocationCkwComponent,
    LocationCkwDetailComponent,
    LocationCkwUpdateComponent,
    LocationCkwDeletePopupComponent,
    LocationCkwDeleteDialogComponent,
    locationRoute,
    locationPopupRoute
} from './';

const ENTITY_STATES = [...locationRoute, ...locationPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocationCkwComponent,
        LocationCkwDetailComponent,
        LocationCkwUpdateComponent,
        LocationCkwDeleteDialogComponent,
        LocationCkwDeletePopupComponent
    ],
    entryComponents: [LocationCkwComponent, LocationCkwUpdateComponent, LocationCkwDeleteDialogComponent, LocationCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2LocationCkwModule {}
