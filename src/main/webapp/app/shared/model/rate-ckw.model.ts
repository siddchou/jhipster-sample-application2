export interface IRateCkw {
    id?: number;
    rateName?: string;
    rateDesc?: string;
    fullRate?: number;
    idleRate?: number;
}

export class RateCkw implements IRateCkw {
    constructor(
        public id?: number,
        public rateName?: string,
        public rateDesc?: string,
        public fullRate?: number,
        public idleRate?: number
    ) {}
}
