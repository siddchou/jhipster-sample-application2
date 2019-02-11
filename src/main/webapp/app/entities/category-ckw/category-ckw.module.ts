import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication2SharedModule } from 'app/shared';
import {
    CategoryCkwComponent,
    CategoryCkwDetailComponent,
    CategoryCkwUpdateComponent,
    CategoryCkwDeletePopupComponent,
    CategoryCkwDeleteDialogComponent,
    categoryRoute,
    categoryPopupRoute
} from './';

const ENTITY_STATES = [...categoryRoute, ...categoryPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplication2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoryCkwComponent,
        CategoryCkwDetailComponent,
        CategoryCkwUpdateComponent,
        CategoryCkwDeleteDialogComponent,
        CategoryCkwDeletePopupComponent
    ],
    entryComponents: [CategoryCkwComponent, CategoryCkwUpdateComponent, CategoryCkwDeleteDialogComponent, CategoryCkwDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2CategoryCkwModule {}
