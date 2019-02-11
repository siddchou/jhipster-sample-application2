/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ServicesCkwDetailComponent } from 'app/entities/services-ckw/services-ckw-detail.component';
import { ServicesCkw } from 'app/shared/model/services-ckw.model';

describe('Component Tests', () => {
    describe('ServicesCkw Management Detail Component', () => {
        let comp: ServicesCkwDetailComponent;
        let fixture: ComponentFixture<ServicesCkwDetailComponent>;
        const route = ({ data: of({ services: new ServicesCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ServicesCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ServicesCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServicesCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.services).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
