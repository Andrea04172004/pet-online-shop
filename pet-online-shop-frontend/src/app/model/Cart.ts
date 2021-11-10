import {LineItem} from "./line-item";
import {Product} from "./Product";
import {Injectable} from "@angular/core";
import {CartService} from "../services/cart-service";

@Injectable({providedIn: 'root'})
export class Cart {
  public id: number;
  public lineItemDtoSet: LineItem[] = [];
}
