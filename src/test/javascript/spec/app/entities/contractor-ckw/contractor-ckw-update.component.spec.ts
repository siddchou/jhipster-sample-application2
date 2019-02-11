/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorCkwUpdateComponent } from 'app/entities/contractor-ckw/contractor-ckw-update.component';
import { ContractorCkwService } from 'app/entities/contractor-ckw/contractor-ckw.service';
import { ContractorCkw } from 'app/shared/model/contractor-ckw.model';

describe('Component Tests', () => {
    describe('ContractorCkw Management Update Component', () => {
        let comp: ContractorCkwUpdateComponent;
        let fixture: ComponentFixture<ContractorCkwUpdateComponent>;
        let service: ContractorCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorCkwUpdateComponent]
            })
                .overrideTemplate(ContractorCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContractorCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContractorCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContractorCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contractor = entity;
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
                    const entity = new ContractorCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contractor = entity;
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
