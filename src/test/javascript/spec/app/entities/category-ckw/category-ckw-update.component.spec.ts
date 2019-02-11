/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { CategoryCkwUpdateComponent } from 'app/entities/category-ckw/category-ckw-update.component';
import { CategoryCkwService } from 'app/entities/category-ckw/category-ckw.service';
import { CategoryCkw } from 'app/shared/model/category-ckw.model';

describe('Component Tests', () => {
    describe('CategoryCkw Management Update Component', () => {
        let comp: CategoryCkwUpdateComponent;
        let fixture: ComponentFixture<CategoryCkwUpdateComponent>;
        let service: CategoryCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [CategoryCkwUpdateComponent]
            })
                .overrideTemplate(CategoryCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoryCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CategoryCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.category = entity;
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
                    const entity = new CategoryCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.category = entity;
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
