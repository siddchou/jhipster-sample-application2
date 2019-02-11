import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    ServicesCkwComponent,
    ServicesCkwDetailComponent,
    ServicesCkwUpdateComponent,
    ServicesCkwDeletePopupComponent,
    ServicesCkwDeleteDialogComponent,
    servicesRoute,
    servicesPopupRoute
} from './';

const ENTITY_STATES = [...servicesRoute, ...servicesPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ServicesCkwComponent,
        ServicesCkwDetailComponent,
        ServicesCkwUpdateComponent,
        ServicesCkwDeleteDialogComponent,
        ServicesCkwDeletePopupComponent
    ],
    entryComponents: [ServicesCkwComponent, ServicesCkwUpdateComponent, ServicesCkwDeleteDialogComponent, ServicesCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2ServicesCkwModule {}
