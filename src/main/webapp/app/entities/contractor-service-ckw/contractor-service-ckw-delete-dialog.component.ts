import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractorServiceCkw } from 'app/shared/model/contractor-service-ckw.model';
import { ContractorServiceCkwService } from './contractor-service-ckw.service';

@Component({
    selector: 'jhi-contractor-service-ckw-delete-dialog',
    templateUrl: './contractor-service-ckw-delete-dialog.component.html'
})
export class ContractorServiceCkwDeleteDialogComponent {
    contractorService: IContractorServiceCkw;

    constructor(
        protected contractorServiceService: ContractorServiceCkwService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contractorServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contractorServiceListModification',
                content: 'Deleted an contractorService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contractor-service-ckw-delete-popup',
    template: ''
})
export class ContractorServiceCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contractorService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContractorServiceCkwDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contractorService = contractorService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/contractor-service-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/contractor-service-ckw', { outlets: { popup: null } }]);
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
