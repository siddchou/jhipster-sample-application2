import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationCkw } from 'app/shared/model/location-ckw.model';
import { LocationCkwService } from './location-ckw.service';

@Component({
    selector: 'jhi-location-ckw-delete-dialog',
    templateUrl: './location-ckw-delete-dialog.component.html'
})
export class LocationCkwDeleteDialogComponent {
    location: ILocationCkw;

    constructor(
        protected locationService: LocationCkwService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.locationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'locationListModification',
                content: 'Deleted an location'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-location-ckw-delete-popup',
    template: ''
})
export class LocationCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ location }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LocationCkwDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.location = location;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/location-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/location-ckw', { outlets: { popup: null } }]);
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
