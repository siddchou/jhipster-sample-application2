/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { RateCkwDetailComponent } from 'app/entities/rate-ckw/rate-ckw-detail.component';
import { RateCkw } from 'app/shared/model/rate-ckw.model';

describe('Component Tests', () => {
    describe('RateCkw Management Detail Component', () => {
        let comp: RateCkwDetailComponent;
        let fixture: ComponentFixture<RateCkwDetailComponent>;
        const route = ({ data: of({ rate: new RateCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [RateCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RateCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RateCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
