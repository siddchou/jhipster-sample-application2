import { NgModule } from '@angular/core';

import { JhipsterSampleApplication2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipsterSampleApplication2SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipsterSampleApplication2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterSampleApplication2SharedCommonModule {}
