import { Moment } from 'moment';
import { IJobTimeLogCkw } from 'app/shared/model/job-time-log-ckw.model';

export const enum JobStatus {
    NOT_STARTED = 'NOT_STARTED',
    PROCESSING = 'PROCESSING',
    COMPLETE = 'COMPLETE',
    ASSIGNED = 'ASSIGNED'
}

export interface IJobHistoryCkw {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    jobStatus?: JobStatus;
    paymentId?: number;
    contractorServiceId?: number;
    userAddressMapId?: number;
    jobTimeLogs?: IJobTimeLogCkw[];
}

export class JobHistoryCkw implements IJobHistoryCkw {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public jobStatus?: JobStatus,
        public paymentId?: number,
        public contractorServiceId?: number,
        public userAddressMapId?: number,
        public jobTimeLogs?: IJobTimeLogCkw[]
    ) {}
}
