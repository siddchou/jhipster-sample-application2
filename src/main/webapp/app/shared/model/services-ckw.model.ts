export interface IServicesCkw {
    id?: number;
    serviceName?: string;
    serviceDescription?: string;
    rateId?: number;
    categoryId?: number;
    locationId?: number;
}

export class ServicesCkw implements IServicesCkw {
    constructor(
        public id?: number,
        public serviceName?: string,
        public serviceDescription?: string,
        public rateId?: number,
        public categoryId?: number,
        public locationId?: number
    ) {}
}
