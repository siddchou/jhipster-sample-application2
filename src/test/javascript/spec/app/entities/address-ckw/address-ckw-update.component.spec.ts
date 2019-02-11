/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { AddressCkwUpdateComponent } from 'app/entities/address-ckw/address-ckw-update.component';
import { AddressCkwService } from 'app/entities/address-ckw/address-ckw.service';
import { AddressCkw } from 'app/shared/model/address-ckw.model';

describe('Component Tests', () => {
    describe('AddressCkw Management Update Component', () => {
        let comp: AddressCkwUpdateComponent;
        let fixture: ComponentFixture<AddressCkwUpdateComponent>;
        let service: AddressCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [AddressCkwUpdateComponent]
            })
                .overrideTemplate(AddressCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AddressCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AddressCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.address = entity;
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
                    const entity = new AddressCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.address = entity;
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
