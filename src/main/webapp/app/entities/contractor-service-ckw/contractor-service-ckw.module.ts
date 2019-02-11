import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    ContractorServiceCkwComponent,
    ContractorServiceCkwDetailComponent,
    ContractorServiceCkwUpdateComponent,
    ContractorServiceCkwDeletePopupComponent,
    ContractorServiceCkwDeleteDialogComponent,
    contractorServiceRoute,
    contractorServicePopupRoute
} from './';

const ENTITY_STATES = [...contractorServiceRoute, ...contractorServicePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContractorServiceCkwComponent,
        ContractorServiceCkwDetailComponent,
        ContractorServiceCkwUpdateComponent,
        ContractorServiceCkwDeleteDialogComponent,
        ContractorServiceCkwDeletePopupComponent
    ],
    entryComponents: [
        ContractorServiceCkwComponent,
        ContractorServiceCkwUpdateComponent,
        ContractorServiceCkwDeleteDialogComponent,
        ContractorServiceCkwDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2ContractorServiceCkwModule {}
