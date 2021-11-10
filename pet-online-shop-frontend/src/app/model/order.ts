import {LineItem} from "./line-item";
import {User} from "./User";
import {Address} from "./address";
import {Payment} from "./payment";
import {Discount} from "./discount";
import {OrderStatuses} from "./enums/order-status.enum";

export class Order {

  constructor(
  public id?: number,
  public status?: OrderStatuses,
  public lineItemDtoSet?: LineItem[],
  public orderPrice?: number,
  public userShortResponseDto?: User,
  public created?: Date,
  public addressDto?: Address,
  public paymentsDto?: Payment[],
  public discountDto?: Discount){
}



}
