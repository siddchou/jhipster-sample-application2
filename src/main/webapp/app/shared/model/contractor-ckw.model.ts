import { Moment } from 'moment';

export interface IContractorCkw {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    hireDate?: Moment;
    startDate?: Moment;
    endDate?: Moment;
}

export class ContractorCkw implements IContractorCkw {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public hireDate?: Moment,
        public startDate?: Moment,
        public endDate?: Moment
    ) {}
}
