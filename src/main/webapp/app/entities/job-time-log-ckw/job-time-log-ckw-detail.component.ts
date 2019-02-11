import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

@Component({
    selector: 'jhi-job-time-log-ckw-detail',
    templateUrl: './job-time-log-ckw-detail.component.html'
})
export class JobTimeLogCkwDetailComponent implements OnInit {
    jobTimeLog: IJobTimeLogCkw;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobTimeLog }) => {
            this.jobTimeLog = jobTimeLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
