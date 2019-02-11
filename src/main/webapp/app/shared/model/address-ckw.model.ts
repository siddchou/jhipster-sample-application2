import { Moment } from 'moment';

export interface IAddressCkw {
    id?: number;
    addressNickName?: string;
    addressLine1?: string;
    addressLine2?: string;
    city?: string;
    state?: string;
    country?: string;
    postalCode?: string;
    startDate?: Moment;
    endDate?: Moment;
    appUserId?: number;
}

export class AddressCkw implements IAddressCkw {
    constructor(
        public id?: number,
        public addressNickName?: string,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public state?: string,
        public country?: string,
        public postalCode?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public appUserId?: number
    ) {}
}
