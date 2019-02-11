/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { RateCkwUpdateComponent } from 'app/entities/rate-ckw/rate-ckw-update.component';
import { RateCkwService } from 'app/entities/rate-ckw/rate-ckw.service';
import { RateCkw } from 'app/shared/model/rate-ckw.model';

describe('Component Tests', () => {
    describe('RateCkw Management Update Component', () => {
        let comp: RateCkwUpdateComponent;
        let fixture: ComponentFixture<RateCkwUpdateComponent>;
        let service: RateCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [RateCkwUpdateComponent]
            })
                .overrideTemplate(RateCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RateCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RateCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RateCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rate = entity;
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
                    const entity = new RateCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rate = entity;
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
