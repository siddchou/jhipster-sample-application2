import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    ContractorCkwComponent,
    ContractorCkwDetailComponent,
    ContractorCkwUpdateComponent,
    ContractorCkwDeletePopupComponent,
    ContractorCkwDeleteDialogComponent,
    contractorRoute,
    contractorPopupRoute
} from './';

const ENTITY_STATES = [...contractorRoute, ...contractorPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContractorCkwComponent,
        ContractorCkwDetailComponent,
        ContractorCkwUpdateComponent,
        ContractorCkwDeleteDialogComponent,
        ContractorCkwDeletePopupComponent
    ],
    entryComponents: [
        ContractorCkwComponent,
        ContractorCkwUpdateComponent,
        ContractorCkwDeleteDialogComponent,
        ContractorCkwDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2ContractorCkwModule {}
