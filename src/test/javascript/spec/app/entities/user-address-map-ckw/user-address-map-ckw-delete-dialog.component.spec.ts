/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { UserAddressMapCkwDeleteDialogComponent } from 'app/entities/user-address-map-ckw/user-address-map-ckw-delete-dialog.component';
import { UserAddressMapCkwService } from 'app/entities/user-address-map-ckw/user-address-map-ckw.service';

describe('Component Tests', () => {
    describe('UserAddressMapCkw Management Delete Component', () => {
        let comp: UserAddressMapCkwDeleteDialogComponent;
        let fixture: ComponentFixture<UserAddressMapCkwDeleteDialogComponent>;
        let service: UserAddressMapCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [UserAddressMapCkwDeleteDialogComponent]
            })
                .overrideTemplate(UserAddressMapCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserAddressMapCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddressMapCkwService);
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
