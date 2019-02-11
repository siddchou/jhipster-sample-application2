/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { LocationCkwUpdateComponent } from 'app/entities/location-ckw/location-ckw-update.component';
import { LocationCkwService } from 'app/entities/location-ckw/location-ckw.service';
import { LocationCkw } from 'app/shared/model/location-ckw.model';

describe('Component Tests', () => {
    describe('LocationCkw Management Update Component', () => {
        let comp: LocationCkwUpdateComponent;
        let fixture: ComponentFixture<LocationCkwUpdateComponent>;
        let service: LocationCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [LocationCkwUpdateComponent]
            })
                .overrideTemplate(LocationCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocationCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LocationCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.location = entity;
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
                    const entity = new LocationCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.location = entity;
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
