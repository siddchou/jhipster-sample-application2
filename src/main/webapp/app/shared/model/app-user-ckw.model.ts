import { Moment } from 'moment';
import { IAddressCkw } from 'app/shared/model/address-ckw.model';

export interface IAppUserCkw {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    startDate?: Moment;
    endDate?: Moment;
    addresses?: IAddressCkw[];
}

export class AppUserCkw implements IAppUserCkw {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public addresses?: IAddressCkw[]
    ) {}
}
