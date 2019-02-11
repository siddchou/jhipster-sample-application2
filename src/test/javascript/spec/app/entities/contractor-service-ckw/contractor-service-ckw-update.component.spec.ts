/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { ContractorServiceCkwUpdateComponent } from 'app/entities/contractor-service-ckw/contractor-service-ckw-update.component';
import { ContractorServiceCkwService } from 'app/entities/contractor-service-ckw/contractor-service-ckw.service';
import { ContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';

describe('Component Tests', () => {
    describe('ContractorServiceCkw Management Update Component', () => {
        let comp: ContractorServiceCkwUpdateComponent;
        let fixture: ComponentFixture<ContractorServiceCkwUpdateComponent>;
        let service: ContractorServiceCkwService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [ContractorServiceCkwUpdateComponent]
            })
                .overrideTemplate(ContractorServiceCkwUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContractorServiceCkwUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContractorServiceCkwService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContractorServiceCkw(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contractorService = entity;
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
                    const entity = new ContractorServiceCkw();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contractorService = entity;
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
