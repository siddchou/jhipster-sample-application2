/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { UserAddressMapCkwDetailComponent } from 'app/entities/user-address-map-ckw/user-address-map-ckw-detail.component';
import { UserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';

describe('Component Tests', () => {
    describe('UserAddressMapCkw Management Detail Component', () => {
        let comp: UserAddressMapCkwDetailComponent;
        let fixture: ComponentFixture<UserAddressMapCkwDetailComponent>;
        const route = ({ data: of({ userAddressMap: new UserAddressMapCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [UserAddressMapCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserAddressMapCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserAddressMapCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userAddressMap).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
