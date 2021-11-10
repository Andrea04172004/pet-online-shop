import {Product} from "./Product";

export class LineItem {

  constructor(
    public id?: number,
    public quantity?: number,
    public product?: Product,
    public subPrice?: number,
  ) {}


}
