/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorServiceCkwDeleteDialogComponent } from 'app/entities/contractor-service-ckw/contractor-service-ckw-delete-dialog.component';
import { ContractorServiceCkwService } from 'app/entities/contractor-service-ckw/contractor-service-ckw.service';

describe('Component Tests', () => {
    describe('ContractorServiceCkw Management Delete Component', () => {
        let comp: ContractorServiceCkwDeleteDialogComponent;
        let fixture: ComponentFixture<ContractorServiceCkwDeleteDialogComponent>;
        let service: ContractorServiceCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorServiceCkwDeleteDialogComponent]
            })
                .overrideTemplate(ContractorServiceCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContractorServiceCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContractorServiceCkwService);
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
