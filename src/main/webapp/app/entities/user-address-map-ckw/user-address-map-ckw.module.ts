import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    UserAddressMapCkwComponent,
    UserAddressMapCkwDetailComponent,
    UserAddressMapCkwUpdateComponent,
    UserAddressMapCkwDeletePopupComponent,
    UserAddressMapCkwDeleteDialogComponent,
    userAddressMapRoute,
    userAddressMapPopupRoute
} from './';

const ENTITY_STATES = [...userAddressMapRoute, ...userAddressMapPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserAddressMapCkwComponent,
        UserAddressMapCkwDetailComponent,
        UserAddressMapCkwUpdateComponent,
        UserAddressMapCkwDeleteDialogComponent,
        UserAddressMapCkwDeletePopupComponent
    ],
    entryComponents: [
        UserAddressMapCkwComponent,
        UserAddressMapCkwUpdateComponent,
        UserAddressMapCkwDeleteDialogComponent,
        UserAddressMapCkwDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2UserAddressMapCkwModule {}
