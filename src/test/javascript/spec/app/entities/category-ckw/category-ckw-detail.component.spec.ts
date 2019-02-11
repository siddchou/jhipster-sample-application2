/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication2TestModule } from '../../../test.module';
import { CategoryCkwDetailComponent } from 'app/entities/category-ckw/category-ckw-detail.component';
import { CategoryCkw } from 'app/shared/model/category-ckw.model';

describe('Component Tests', () => {
    describe('CategoryCkw Management Detail Component', () => {
        let comp: CategoryCkwDetailComponent;
        let fixture: ComponentFixture<CategoryCkwDetailComponent>;
        const route = ({ data: of({ category: new CategoryCkw(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplication2TestModule],
                declarations: [CategoryCkwDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoryCkwDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryCkwDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.category).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
