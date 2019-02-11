/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { PaymentCkwDeleteDialogComponent } from 'app/entities/payment-ckw/payment-ckw-delete-dialog.component';
import { PaymentCkwService } from 'app/entities/payment-ckw/payment-ckw.service';

describe('Component Tests', () => {
    describe('PaymentCkw Management Delete Component', () => {
        let comp: PaymentCkwDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentCkwDeleteDialogComponent>;
        let service: PaymentCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [PaymentCkwDeleteDialogComponent]
            })
                .overrideTemplate(PaymentCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentCkwService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
