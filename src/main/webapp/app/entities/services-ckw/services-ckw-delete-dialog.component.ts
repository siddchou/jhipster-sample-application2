import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServicesCkw } from 'app/shared/model/services-ckw.model';
import { ServicesCkwService } from './services-ckw.service';

@Component({
    selector: 'jhi-services-ckw-delete-dialog',
    templateUrl: './services-ckw-delete-dialog.component.html'
})
export class ServicesCkwDeleteDialogComponent {
    services: IServicesCkw;

    constructor(
        protected servicesService: ServicesCkwService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servicesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'servicesListModification',
                content: 'Deleted an services'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-services-ckw-delete-popup',
    template: ''
})
export class ServicesCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ services }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ServicesCkwDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.services = services;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/services-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/services-ckw', { outlets: { popup: null } }]);
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
