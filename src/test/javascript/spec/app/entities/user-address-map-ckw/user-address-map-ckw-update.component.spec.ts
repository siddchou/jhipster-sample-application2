/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { UserAddressMapCkwUpdateComponent } from 'app/entities/user-address-map-ckw/user-address-map-ckw-update.component';
import { UserAddressMapCkwService } from 'app/entities/user-address-map-ckw/user-address-map-ckw.service';
import { UserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';

describe('Component Tests', () => {
    describe('UserAddressMapCkw Management Update Component', () => {
        let comp: UserAddressMapCkwUpdateComponent;
        let fixture: ComponentFixture<UserAddressMapCkwUpdateComponent>;
        let service: UserAddressMapCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [UserAddressMapCkwUpdateComponent]
            })
                .overrideTemplate(UserAddressMapCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserAddressMapCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddressMapCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserAddressMapCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userAddressMap = entity;
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
                    const entity = new UserAddressMapCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userAddressMap = entity;
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
