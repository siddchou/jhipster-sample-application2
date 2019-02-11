/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { CategoryCkwDeleteDialogComponent } from 'app/entities/category-ckw/category-ckw-delete-dialog.component';
import { CategoryCkwService } from 'app/entities/category-ckw/category-ckw.service';

describe('Component Tests', () => {
    describe('CategoryCkw Management Delete Component', () => {
        let comp: CategoryCkwDeleteDialogComponent;
        let fixture: ComponentFixture<CategoryCkwDeleteDialogComponent>;
        let service: CategoryCkwService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [CategoryCkwDeleteDialogComponent]
            })
                .overrideTemplate(CategoryCkwDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryCkwDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryCkwService);
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
