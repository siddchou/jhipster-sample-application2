/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { JobTimeLogCkwUpdateComponent } from 'app/entities/job-time-log-ckw/job-time-log-ckw-update.component';
import { JobTimeLogCkwService } from 'app/entities/job-time-log-ckw/job-time-log-ckw.service';
import { JobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

describe('Component Tests', () => {
    describe('JobTimeLogCkw Management Update Component', () => {
        let comp: JobTimeLogCkwUpdateComponent;
        let fixture: ComponentFixture<JobTimeLogCkwUpdateComponent>;
        let service: JobTimeLogCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [JobTimeLogCkwUpdateComponent]
            })
                .overrideTemplate(JobTimeLogCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobTimeLogCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobTimeLogCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobTimeLogCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobTimeLog = entity;
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
                    const entity = new JobTimeLogCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobTimeLog = entity;
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
