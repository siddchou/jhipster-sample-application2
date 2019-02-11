import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRateCkw } from 'app/shared/model/rate-ckw.model';
import { RateCkwService } from './rate-ckw.service';

@Component({
    selector: 'jhi-rate-ckw-delete-dialog',
    templateUrl: './rate-ckw-delete-dialog.component.html'
})
export class RateCkwDeleteDialogComponent {
    rate: IRateCkw;

    constructor(protected rateService: RateCkwService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rateListModification',
                content: 'Deleted an rate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rate-ckw-delete-popup',
    template: ''
})
export class RateCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RateCkwDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rate = rate;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rate-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rate-ckw', { outlets: { popup: null } }]);
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
