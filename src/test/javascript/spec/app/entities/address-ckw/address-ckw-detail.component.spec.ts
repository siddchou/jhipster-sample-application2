/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { AddressCkwDetailComponent } from 'app/entities/address-ckw/address-ckw-detail.component';
import { AddressCkw } from 'app/shared/model/address-ckw.model';

describe('Component Tests', () => {
    describe('AddressCkw Management Detail Component', () => {
        let comp: AddressCkwDetailComponent;
        let fixture: ComponentFixture<AddressCkwDetailComponent>;
        const route = ({ data: of({ address: new AddressCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [AddressCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AddressCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AddressCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.address).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
