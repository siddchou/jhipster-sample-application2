import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserAddressMapCkw } from 'app/shared/model/user-address-map-ckw.model';
import { UserAddressMapCkwService } from './user-address-map-ckw.service';

@Component({
    selector: 'jhi-user-address-map-ckw-delete-dialog',
    templateUrl: './user-address-map-ckw-delete-dialog.component.html'
})
export class UserAddressMapCkwDeleteDialogComponent {
    userAddressMap: IUserAddressMapCkw;

    constructor(
        protected userAddressMapService: UserAddressMapCkwService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAddressMapService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userAddressMapListModification',
                content: 'Deleted an userAddressMap'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-address-map-ckw-delete-popup',
    template: ''
})
export class UserAddressMapCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userAddressMap }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserAddressMapCkwDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userAddressMap = userAddressMap;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-address-map-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-address-map-ckw', { outlets: { popup: null } }]);
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
