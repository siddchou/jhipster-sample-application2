import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryCkw } from 'app/shared/model/category-ckw.model';

@Component({
    selector: 'jhi-category-ckw-detail',
    templateUrl: './category-ckw-detail.component.html'
})
export class CategoryCkwDetailComponent implements OnInit {
    category: ICategoryCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
        });
    }

    previousState() {
        window.history.back();
    }
}
