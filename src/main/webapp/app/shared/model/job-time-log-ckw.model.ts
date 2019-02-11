import { Moment } from 'moment';

export interface IJobTimeLogCkw {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    isValidated?: boolean;
    jobHistoryId?: number;
}

export class JobTimeLogCkw implements IJobTimeLogCkw {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public isValidated?: boolean,
        public jobHistoryId?: number
    ) {
        this.isValidated = this.isValidated || false;
    }
}
