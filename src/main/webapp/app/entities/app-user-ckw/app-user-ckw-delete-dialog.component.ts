import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppUserCkw } from 'app/shared/model/app-user-ckw.model';
import { AppUserCkwService } from './app-user-ckw.service';

@Component({
    selector: 'jhi-app-user-ckw-delete-dialog',
    templateUrl: './app-user-ckw-delete-dialog.component.html'
})
export class AppUserCkwDeleteDialogComponent {
    appUser: IAppUserCkw;

    constructor(protected appUserService: AppUserCkwService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appUserListModification',
                content: 'Deleted an appUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-user-ckw-delete-popup',
    template: ''
})
export class AppUserCkwDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AppUserCkwDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.appUser = appUser;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/app-user-ckw', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/app-user-ckw', { outlets: { popup: null } }]);
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
