/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { PaymentCkwUpdateComponent } from 'app/entities/payment-ckw/payment-ckw-update.component';
import { PaymentCkwService } from 'app/entities/payment-ckw/payment-ckw.service';
import { PaymentCkw } from 'app/shared/model/payment-ckw.model';

describe('Component Tests', () => {
    describe('PaymentCkw Management Update Component', () => {
        let comp: PaymentCkwUpdateComponent;
        let fixture: ComponentFixture<PaymentCkwUpdateComponent>;
        let service: PaymentCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [PaymentCkwUpdateComponent]
            })
                .overrideTemplate(PaymentCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PaymentCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.payment = entity;
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
                    const entity = new PaymentCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.payment = entity;
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
