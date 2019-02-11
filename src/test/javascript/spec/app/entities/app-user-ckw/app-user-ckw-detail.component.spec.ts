/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { AppUserCkwDetailComponent } from 'app/entities/app-user-ckw/app-user-ckw-detail.component';
import { AppUserCkw } from 'app/shared/model/app-user-ckw.model';

describe('Component Tests', () => {
    describe('AppUserCkw Management Detail Component', () => {
        let comp: AppUserCkwDetailComponent;
        let fixture: ComponentFixture<AppUserCkwDetailComponent>;
        const route = ({ data: of({ appUser: new AppUserCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [AppUserCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AppUserCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppUserCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
