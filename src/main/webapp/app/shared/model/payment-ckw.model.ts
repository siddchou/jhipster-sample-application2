import { Moment } from 'moment';

export const enum PaymentType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT',
    PAYPAL = 'PAYPAL'
}

export interface IPaymentCkw {
    id?: number;
    totalPaymentAmount?: number;
    paymentType?: PaymentType;
    contractorAmount?: number;
    businessAmount?: number;
    startDate?: Moment;
    endDate?: Moment;
}

export class PaymentCkw implements IPaymentCkw {
    constructor(
        public id?: number,
        public totalPaymentAmount?: number,
        public paymentType?: PaymentType,
        public contractorAmount?: number,
        public businessAmount?: number,
        public startDate?: Moment,
        public endDate?: Moment
    ) {}
}
