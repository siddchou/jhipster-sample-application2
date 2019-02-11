/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { JobHistoryCkwUpdateComponent } from 'app/entities/job-history-ckw/job-history-ckw-update.component';
import { JobHistoryCkwService } from 'app/entities/job-history-ckw/job-history-ckw.service';
import { JobHistoryCkw } from 'app/shared/model/job-history-ckw.model';

describe('Component Tests', () => {
    describe('JobHistoryCkw Management Update Component', () => {
        let comp: JobHistoryCkwUpdateComponent;
        let fixture: ComponentFixture<JobHistoryCkwUpdateComponent>;
        let service: JobHistoryCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [JobHistoryCkwUpdateComponent]
            })
                .overrideTemplate(JobHistoryCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobHistoryCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobHistoryCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobHistoryCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
