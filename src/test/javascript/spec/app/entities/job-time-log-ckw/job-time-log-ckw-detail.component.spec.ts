/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { JobTimeLogCkwDetailComponent } from 'app/entities/job-time-log-ckw/job-time-log-ckw-detail.component';
import { JobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

describe('Component Tests', () => {
    describe('JobTimeLogCkw Management Detail Component', () => {
        let comp: JobTimeLogCkwDetailComponent;
        let fixture: ComponentFixture<JobTimeLogCkwDetailComponent>;
        const route = ({ data: of({ jobTimeLog: new JobTimeLogCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [JobTimeLogCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobTimeLogCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobTimeLogCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobTimeLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
