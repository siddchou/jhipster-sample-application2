import { Moment } from 'moment';

export interface IContractorServiceCkw {
    id?: number;
    isVerified?: boolean;
    startDate?: Moment;
    endDate?: Moment;
    contractorId?: number;
    servicesId?: number;
}

export class ContractorServiceCkw implements IContractorServiceCkw {
    constructor(
        public id?: number,
        public isVerified?: boolean,
        public startDate?: Moment,
        public endDate?: Moment,
        public contractorId?: number,
        public servicesId?: number
    ) {
        this.isVerified = this.isVerified || false;
    }
}
