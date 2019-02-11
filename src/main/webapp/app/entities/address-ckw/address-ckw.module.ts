import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    AddressCkwComponent,
    AddressCkwDetailComponent,
    AddressCkwUpdateComponent,
    AddressCkwDeletePopupComponent,
    AddressCkwDeleteDialogComponent,
    addressRoute,
    addressPopupRoute
} from './';

const ENTITY_STATES = [...addressRoute, ...addressPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AddressCkwComponent,
        AddressCkwDetailComponent,
        AddressCkwUpdateComponent,
        AddressCkwDeleteDialogComponent,
        AddressCkwDeletePopupComponent
    ],
    entryComponents: [AddressCkwComponent, AddressCkwUpdateComponent, AddressCkwDeleteDialogComponent, AddressCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2AddressCkwModule {}
