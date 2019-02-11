export interface ICategoryCkw {
    id?: number;
    categoryName?: string;
    categoryDescription?: string;
}

export class CategoryCkw implements ICategoryCkw {
    constructor(public id?: number, public categoryName?: string, public categoryDescription?: string) {}
}
