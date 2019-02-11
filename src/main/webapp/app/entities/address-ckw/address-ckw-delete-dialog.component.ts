import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressCkw } from 'app/shared/model/address-ckw.model';
import { AddressCkwService } from './address-ckw.service';

@Component({
    selector: 'jhi-address-ckw-delete-dialog',
    templateUrl: './address-ckw-delete-dialog.component.html'
})
export class AddressCkwDeleteDialogComponent {
    address: IAddressCkw;

    constructor(protected addressService: AddressCkwService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'addressListModification',
                content: 'Deleted an address'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-ckw-delete-popup',
    template: ''
})
export class AddressCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ address }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AddressCkwDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.address = address;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/address-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/address-ckw', { outlets: { popup: null } }]);
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
