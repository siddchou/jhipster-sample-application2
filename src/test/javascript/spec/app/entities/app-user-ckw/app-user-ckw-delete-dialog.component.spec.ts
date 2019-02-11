/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { AppUserCkwDeleteDialogComponent } from 'app/entities/app-user-ckw/app-user-ckw-delete-dialog.component';
import { AppUserCkwService } from 'app/entities/app-user-ckw/app-user-ckw.service';

describe('Component Tests', () => {
    describe('AppUserCkw Management Delete Component', () => {
        let comp: AppUserCkwDeleteDialogComponent;
        let fixture: ComponentFixture<AppUserCkwDeleteDialogComponent>;
        let service: AppUserCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [AppUserCkwDeleteDialogComponent]
            })
                .overrideTemplate(AppUserCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppUserCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppUserCkwService);
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
