import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobHistoryCkw } from 'app/shared/model/job-history-ckw.model';

@Component({
    selector: 'jhi-job-history-ckw-detail',
    templateUrl: './job-history-ckw-detail.component.html'
})
export class JobHistoryCkwDetailComponent implements OnInit {
    jobHistory: IJobHistoryCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobHistory }) => {
            this.jobHistory = jobHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
