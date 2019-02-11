import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IContractorCkw } from 'app/shared/model/contractor-ckw.model';
import { ContractorCkwService } from './contractor-ckw.service';

@Component({
    selector: 'jhi-contractor-ckw-update',
    templateUrl: './contractor-ckw-update.component.html'
})
export class ContractorCkwUpdateComponent implements OnInit {
    contractor: IContractorCkw;
    isSaving: boolean;
    hireDate: string;
    startDate: string;
    endDate: string;

    constructor(protected contractorService: ContractorCkwService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contractor }) => {
            this.contractor = contractor;
            this.hireDate = this.contractor.hireDate != null ? this.contractor.hireDate.format(DATE_TIME_FORMAT) : null;
            this.startDate = this.contractor.startDate != null ? this.contractor.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.contractor.endDate != null ? this.contractor.endDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.contractor.hireDate = this.hireDate != null ? moment(this.hireDate, DATE_TIME_FORMAT) : null;
        this.contractor.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.contractor.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.contractor.id !== undefined) {
            this.subscribeToSaveResponse(this.contractorService.update(this.contractor));
        } else {
            this.subscribeToSaveResponse(this.contractorService.create(this.contractor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractorCkw>>) {
        result.subscribe((res: HttpResponse<IContractorCkw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
