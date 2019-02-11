/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { LocationCkwDetailComponent } from 'app/entities/location-ckw/location-ckw-detail.component';
import { LocationCkw } from 'app/shared/model/location-ckw.model';

describe('Component Tests', () => {
    describe('LocationCkw Management Detail Component', () => {
        let comp: LocationCkwDetailComponent;
        let fixture: ComponentFixture<LocationCkwDetailComponent>;
        const route = ({ data: of({ location: new LocationCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [LocationCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LocationCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocationCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.location).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
