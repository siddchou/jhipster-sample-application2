/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ServicesCkwUpdateComponent } from 'app/entities/services-ckw/services-ckw-update.component';
import { ServicesCkwService } from 'app/entities/services-ckw/services-ckw.service';
import { ServicesCkw } from 'app/shared/model/services-ckw.model';

describe('Component Tests', () => {
    describe('ServicesCkw Management Update Component', () => {
        let comp: ServicesCkwUpdateComponent;
        let fixture: ComponentFixture<ServicesCkwUpdateComponent>;
        let service: ServicesCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ServicesCkwUpdateComponent]
            })
                .overrideTemplate(ServicesCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServicesCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicesCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ServicesCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.services = entity;
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
                    const entity = new ServicesCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.services = entity;
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
