export interface ILocationCkw {
    id?: number;
    city?: string;
    province?: string;
}

export class LocationCkw implements ILocationCkw {
    constructor(public id?: number, public city?: string, public province?: string) {}
}
