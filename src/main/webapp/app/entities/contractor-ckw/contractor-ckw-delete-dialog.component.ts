import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractorCkw } from 'app/shared/model/contractor-ckw.model';
import { ContractorCkwService } from './contractor-ckw.service';

@Component({
    selector: 'jhi-contractor-ckw-delete-dialog',
    templateUrl: './contractor-ckw-delete-dialog.component.html'
})
export class ContractorCkwDeleteDialogComponent {
    contractor: IContractorCkw;

    constructor(
        protected contractorService: ContractorCkwService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contractorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contractorListModification',
                content: 'Deleted an contractor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contractor-ckw-delete-popup',
    template: ''
})
export class ContractorCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contractor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContractorCkwDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contractor = contractor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/contractor-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/contractor-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
