import {Product} from "./Product";

export class WishList{

  constructor(
    public id?: number,
    public title?: string,
    public productDto?: Product[]
  ){

  }

}
