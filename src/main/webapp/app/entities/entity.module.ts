import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'category-ckw',
                loadChildren: './category-ckw/category-ckw.module#JhipsterSampleApplication2CategoryCkwModule'
            },
            {
                path: 'services-ckw',
                loadChildren: './services-ckw/services-ckw.module#JhipsterSampleApplication2ServicesCkwModule'
            },
            {
                path: 'contractor-service-ckw',
                loadChildren: './contractor-service-ckw/contractor-service-ckw.module#JhipsterSampleApplication2ContractorServiceCkwModule'
            },
            {
                path: 'user-address-map-ckw',
                loadChildren: './user-address-map-ckw/user-address-map-ckw.module#JhipsterSampleApplication2UserAddressMapCkwModule'
            },
            {
                path: 'contractor-ckw',
                loadChildren: './contractor-ckw/contractor-ckw.module#JhipsterSampleApplication2ContractorCkwModule'
            },
            {
                path: 'location-ckw',
                loadChildren: './location-ckw/location-ckw.module#JhipsterSampleApplication2LocationCkwModule'
            },
            {
                path: 'rate-ckw',
                loadChildren: './rate-ckw/rate-ckw.module#JhipsterSampleApplication2RateCkwModule'
            },
            {
                path: 'app-user-ckw',
                loadChildren: './app-user-ckw/app-user-ckw.module#JhipsterSampleApplication2AppUserCkwModule'
            },
            {
                path: 'address-ckw',
                loadChildren: './address-ckw/address-ckw.module#JhipsterSampleApplication2AddressCkwModule'
            },
            {
                path: 'job-history-ckw',
                loadChildren: './job-history-ckw/job-history-ckw.module#JhipsterSampleApplication2JobHistoryCkwModule'
            },
            {
                path: 'job-time-log-ckw',
                loadChildren: './job-time-log-ckw/job-time-log-ckw.module#JhipsterSampleApplication2JobTimeLogCkwModule'
            },
            {
                path: 'payment-ckw',
                loadChildren: './payment-ckw/payment-ckw.module#JhipsterSampleApplication2PaymentCkwModule'
            },
            {
                path: 'category-ckw',
                loadChildren: './category-ckw/category-ckw.module#JhipsterSampleApplication2CategoryCkwModule'
            },
            {
                path: 'services-ckw',
                loadChildren: './services-ckw/services-ckw.module#JhipsterSampleApplication2ServicesCkwModule'
            },
            {
                path: 'contractor-service-ckw',
                loadChildren: './contractor-service-ckw/contractor-service-ckw.module#JhipsterSampleApplication2ContractorServiceCkwModule'
            },
            {
                path: 'user-address-map-ckw',
                loadChildren: './user-address-map-ckw/user-address-map-ckw.module#JhipsterSampleApplication2UserAddressMapCkwModule'
            },
            {
                path: 'contractor-ckw',
                loadChildren: './contractor-ckw/contractor-ckw.module#JhipsterSampleApplication2ContractorCkwModule'
            },
            {
                path: 'location-ckw',
                loadChildren: './location-ckw/location-ckw.module#JhipsterSampleApplication2LocationCkwModule'
            },
            {
                path: 'rate-ckw',
                loadChildren: './rate-ckw/rate-ckw.module#JhipsterSampleApplication2RateCkwModule'
            },
            {
                path: 'app-user-ckw',
                loadChildren: './app-user-ckw/app-user-ckw.module#JhipsterSampleApplication2AppUserCkwModule'
            },
            {
                path: 'address-ckw',
                loadChildren: './address-ckw/address-ckw.module#JhipsterSampleApplication2AddressCkwModule'
            },
            {
                path: 'job-history-ckw',
                loadChildren: './job-history-ckw/job-history-ckw.module#JhipsterSampleApplication2JobHistoryCkwModule'
            },
            {
                path: 'job-time-log-ckw',
                loadChildren: './job-time-log-ckw/job-time-log-ckw.module#JhipsterSampleApplication2JobTimeLogCkwModule'
            },
            {
                path: 'payment-ckw',
                loadChildren: './payment-ckw/payment-ckw.module#JhipsterSampleApplication2PaymentCkwModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplication2EntityModule {}
