/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorServiceCkwDetailComponent } from 'app/entities/contractor-service-ckw/contractor-service-ckw-detail.component';
import { ContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';

describe('Component Tests', () => {
    describe('ContractorServiceCkw Management Detail Component', () => {
        let comp: ContractorServiceCkwDetailComponent;
        let fixture: ComponentFixture<ContractorServiceCkwDetailComponent>;
        const route = ({ data: of({ contractorService: new ContractorServiceCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorServiceCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContractorServiceCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContractorServiceCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contractorService).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
