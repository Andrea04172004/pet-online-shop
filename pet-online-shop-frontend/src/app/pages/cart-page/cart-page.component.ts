import {Component, OnInit} from '@angular/core';
import {Cart} from "../../model/Cart";
import {CartService} from "../../services/cart-service";
import {LineItem} from "../../model/line-item";
import {Product} from "../../model/Product";
import {Line} from "tslint/lib/verify/lines";
import {EventEmitterService} from "../../services/event-emitter.service";
import {delay} from "rxjs/operators";

@Component({
  selector: 'app-card-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent implements OnInit {
  public cart: Cart;
  public lineItemsNumber: number;
  public lineItem: LineItem;
  public lineItemDtoSet: LineItem[] = [];

  public itemCount: number;
  public cartPrice: number;
  public show : boolean = false;
  public validationResponse: string;
  public isValid: boolean;

  public emptyCartImage: string = 'https://www.pngkit.com/png/full/116-1169515_cart-png-pic-shopping-cart-logo-png-white.png';
  ngOnInit(): void {
    this.cartService.getUserCart().subscribe((data) => {
      this.cart = data;
      this.lineItemDtoSet = data.lineItemDtoSet;
      this.lineItemsNumber = this.cart.lineItemDtoSet.length;
    })
  }

  constructor(public cartService: CartService,
              private eventEmitterService: EventEmitterService) {

  }

  public cartTotal() {
    this.itemCount = 0;
    this.cartPrice = 0;

    this.lineItemDtoSet.forEach(l => {
      this.itemCount += l.quantity;
      this.cartPrice += (l.quantity * l.product.price);
    });
    return Math.round(this.cartPrice);
  }

  public lineTotal(quantity: number, price: number): number{
    return Math.round(quantity * price);
  }

  public getLineItemsDto(){
    return this.lineItemDtoSet;
  }

  public plusQuantity(lineId: number) {
    this.lineItem = this.cart.lineItemDtoSet.find(data => data.id == lineId);
    if (this.lineItem.quantity > 0 && this.lineItem.quantity < 100) {
      this.lineItem.quantity++;
       this.cartService.updateLineQuantity(lineId, this.lineItem.quantity);
    }
    console.log(this.lineItem.quantity);
  }

  public minusQuantity(lineId: number) {
    this.lineItem = this.cart.lineItemDtoSet.find(data => data.id == lineId);
    if (this.lineItem.quantity > 0 && this.lineItem.quantity < 100) {
      this.lineItem.quantity--;
       this.cartService.updateLineQuantity(lineId, this.lineItem.quantity);
    }
    if (this.lineItem.quantity == 0) {
      this.deleteLineItem(this.lineItem.product.name, lineId)
    }
    console.log(this.lineItem.quantity);
  }


  public deleteLineItem(lineItemName: string, lineItemId: number) {
    this.cartService.deleteLineItemByLine(lineItemId);
    this.lineItemDtoSet.splice(this.lineItemDtoSet.findIndex((lineItem) => lineItem.id == lineItemId), 1);
    setTimeout(()=>{this.eventEmitterService.onAllProductComponentButtonClick()}, 2000);
  }


  public goCheck(cart: Cart){
    console.log("we are here");
    this.cartService.checkCartQuantity(cart).subscribe((data)=>{
      if(data.includes("200")){
        this.isValid = true;
        this.validationResponse = "OK"
      }
      this.isValid = false;
      this.validationResponse = data;
      console.log(this.validationResponse);
    });
  }
}

