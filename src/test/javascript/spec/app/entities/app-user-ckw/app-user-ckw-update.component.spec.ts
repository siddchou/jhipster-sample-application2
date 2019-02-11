/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { AppUserCkwUpdateComponent } from 'app/entities/app-user-ckw/app-user-ckw-update.component';
import { AppUserCkwService } from 'app/entities/app-user-ckw/app-user-ckw.service';
import { AppUserCkw } from 'app/shared/model/app-user-ckw.model';

describe('Component Tests', () => {
    describe('AppUserCkw Management Update Component', () => {
        let comp: AppUserCkwUpdateComponent;
        let fixture: ComponentFixture<AppUserCkwUpdateComponent>;
        let service: AppUserCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [AppUserCkwUpdateComponent]
            })
                .overrideTemplate(AppUserCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AppUserCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppUserCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AppUserCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appUser = entity;
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
                    const entity = new AppUserCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appUser = entity;
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
