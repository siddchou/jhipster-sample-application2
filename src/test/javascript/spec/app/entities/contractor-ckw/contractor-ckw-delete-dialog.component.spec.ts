/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorCkwDeleteDialogComponent } from 'app/entities/contractor-ckw/contractor-ckw-delete-dialog.component';
import { ContractorCkwService } from 'app/entities/contractor-ckw/contractor-ckw.service';

describe('Component Tests', () => {
    describe('ContractorCkw Management Delete Component', () => {
        let comp: ContractorCkwDeleteDialogComponent;
        let fixture: ComponentFixture<ContractorCkwDeleteDialogComponent>;
        let service: ContractorCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorCkwDeleteDialogComponent]
            })
                .overrideTemplate(ContractorCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContractorCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContractorCkwService);
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
