/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ServicesCkwDeleteDialogComponent } from 'app/entities/services-ckw/services-ckw-delete-dialog.component';
import { ServicesCkwService } from 'app/entities/services-ckw/services-ckw.service';

describe('Component Tests', () => {
    describe('ServicesCkw Management Delete Component', () => {
        let comp: ServicesCkwDeleteDialogComponent;
        let fixture: ComponentFixture<ServicesCkwDeleteDialogComponent>;
        let service: ServicesCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ServicesCkwDeleteDialogComponent]
            })
                .overrideTemplate(ServicesCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServicesCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicesCkwService);
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
