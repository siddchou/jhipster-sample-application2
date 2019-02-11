/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { JobHistoryCkwDetailComponent } from 'app/entities/job-history-ckw/job-history-ckw-detail.component';
import { JobHistoryCkw } from 'app/shared/model/job-history-ckw.model';

describe('Component Tests', () => {
    describe('JobHistoryCkw Management Detail Component', () => {
        let comp: JobHistoryCkwDetailComponent;
        let fixture: ComponentFixture<JobHistoryCkwDetailComponent>;
        const route = ({ data: of({ jobHistory: new JobHistoryCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [JobHistoryCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobHistoryCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobHistoryCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
