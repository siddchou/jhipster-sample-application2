import { Moment } from 'moment';

export interface IUserAddressMapCkw {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    appUserId?: number;
    addressId?: number;
}

export class UserAddressMapCkw implements IUserAddressMapCkw {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public appUserId?: number,
        public addressId?: number
    ) {}
}
