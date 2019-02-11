/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { PaymentCkwDetailComponent } from 'app/entities/payment-ckw/payment-ckw-detail.component';
import { PaymentCkw } from 'app/shared/model/payment-ckw.model';

describe('Component Tests', () => {
    describe('PaymentCkw Management Detail Component', () => {
        let comp: PaymentCkwDetailComponent;
        let fixture: ComponentFixture<PaymentCkwDetailComponent>;
        const route = ({ data: of({ payment: new PaymentCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [PaymentCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaymentCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.payment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
