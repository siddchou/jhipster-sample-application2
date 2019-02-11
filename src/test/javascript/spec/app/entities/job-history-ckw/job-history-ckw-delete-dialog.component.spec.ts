/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { JobHistoryCkwDeleteDialogComponent } from 'app/entities/job-history-ckw/job-history-ckw-delete-dialog.component';
import { JobHistoryCkwService } from 'app/entities/job-history-ckw/job-history-ckw.service';

describe('Component Tests', () => {
    describe('JobHistoryCkw Management Delete Component', () => {
        let comp: JobHistoryCkwDeleteDialogComponent;
        let fixture: ComponentFixture<JobHistoryCkwDeleteDialogComponent>;
        let service: JobHistoryCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [JobHistoryCkwDeleteDialogComponent]
            })
                .overrideTemplate(JobHistoryCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobHistoryCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryCkwService);
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
