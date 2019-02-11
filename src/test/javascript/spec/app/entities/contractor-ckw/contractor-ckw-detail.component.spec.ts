/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorCkwDetailComponent } from 'app/entities/contractor-ckw/contractor-ckw-detail.component';
import { ContractorCkw } from 'app/shared/model/contractor-ckw.model';

describe('Component Tests', () => {
    describe('ContractorCkw Management Detail Component', () => {
        let comp: ContractorCkwDetailComponent;
        let fixture: ComponentFixture<ContractorCkwDetailComponent>;
        const route = ({ data: of({ contractor: new ContractorCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContractorCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContractorCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contractor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
