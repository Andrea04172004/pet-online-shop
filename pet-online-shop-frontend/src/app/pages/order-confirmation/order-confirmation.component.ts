import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart-service";
import {Cart} from "../../model/Cart";
import {LineItem} from "../../model/line-item";
import {Observable, timer} from "rxjs";
import {mergeMap} from "rxjs/operators";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FullCheckoutComponent} from "../full-checkout/full-checkout.component";
import {OrderService} from "../../services/order-service";
import {WishlistService} from "../../services/wishlist-service";

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrls: ['./order-confirmation.component.css']
})
export class OrderConfirmationComponent implements OnInit {

  public carts: Cart[];
  public cart: Cart;
  public cartId: string;
  public notAvailableProducts: Product[];
  public lineItems: LineItem[] = [];
  public lineItem = LineItem;
  public totalAmount: number;
  public access: boolean = true;
  private settingForm: FormGroup;
  private fb: FormBuilder;
  private isntAvailable = false;
  private overLimit: boolean;
  private notificationText: string;
  // public lineItemsWithNotAvailableProducts: LineItem[] = [];
  private notifications: string[] = ['Not enough products, available ', 'Price of product was changed', 'Out of stock'];
  private notEnoughNotification: string = 'Not enough products, available ';

  private notificationsObj: {[key: string]: string}={
    notEnough: 'Not enough products, available ',
    anotherPrice: 'Price of product was changed',
    outOfStock: 'Out of stock'
  };

  constructor(public cartService: CartService, public wishListService: WishlistService, private route: ActivatedRoute, public orderService: OrderService,
              private fullCheck: FullCheckoutComponent) {
    this.cartId = this.route.snapshot.paramMap.get('id');

    this.cartService.getUserCart().subscribe((data) =>{
      this.cart = data;
      this.lineItems = data.lineItemDtoSet;
      this.orderService.tempOrder.lineItemDtoSet = data.lineItemDtoSet;
      console.log(this.cart);
      console.log(this.lineItems)

    });

    timer(500).subscribe(()=>
    this.cartService.checkNotAvailableProducts(this.cart).subscribe((data) =>{
      this.notAvailableProducts = data;
      if(data.length == 0){
        this.fullCheck.firstStep = true;
      }
      console.log('length'+data.length)
      console.log(data);
    }))

  }

  ngOnInit(): void {
    // for(let line of this.cart.lineItemDtoSet){
    //   this.settingForm = this.fb.group({
    //     quantity: new FormControl(1, [
    //       Validators.required,
    //       Validators.min(1),
    //       Validators.max(5)
    //     ])
    //   })
    // }

    // for(let line of this.cart.lineItemDtoSet) {
    //   this.settingForm = this.fb.group({
    //     quantities: this.fb.array([line.quantity])
    //   });
    // }


    // for (let line of this.lineItems){
    //   for (let prod of this.notAvailableProducts) {
    //     if(line.product.id == prod.id){
    //       this.isntAvailable = true;
    //     }
    //   }
    // }

  }



  onClick(){

  }

  onSubmit(){
    // this.orderService.tempOrder.lineItemDtoSet = this.lineItems;
      console.log(this.orderService.tempOrder);
  }

  public getCartById(id: number): Cart {
    return this.carts.find((x) => x.id === id);
  }

  public deleteLineItemByProdId(){
    if(this.notAvailableProducts.length > 0) {
      for (let p of this.notAvailableProducts) {
        this.lineItems.splice(this.lineItems.findIndex((x)=> p.name == x.product.name),1)
        this.cartService.deleteLineByProductId(p.id)
      }

    }
    this.notAvailableProducts.splice(0,this.notAvailableProducts.length);

    this.orderService.tempOrder.lineItemDtoSet = this.lineItems;
    console.log(this.orderService.tempOrder);
    this.fullCheck.firstStep = true;
  }


  public getLineItems(){
    return this.lineItems;
  }

public getTotalAmount(): number{
    this.totalAmount = 0;
    this.lineItems.forEach(lit=>{
      this.totalAmount += lit.quantity*lit.product.price;
    });
  return Math.round(this.totalAmount);
    }


  // increament() {
  //   this.settingForm.setValue({
  //     quantity: this.settingForm.get("quantity").value + 1
  //   });
  // }
  // decreament() {
  //   this.settingForm.setValue({
  //     quantity: this.settingForm.get("quantity").value - 1
  //   });
  // }


  public plus(lineId: number) {
    let lit: LineItem;
      lit = this.cart.lineItemDtoSet.find(data => data.id == lineId);
     // if(lit.quantity > 0) {
        lit.quantity++;
        console.log("Value of " + lit.product.name + " after increment ", lit.quantity);
        this.cartService.updateLineQuantity(lineId, lit.quantity);
     // }
  }

  public minus(lineId: number) {
    let lit: LineItem;
    lit = this.cart.lineItemDtoSet.find(data=> data.id == lineId);
    if(lit.quantity > 0) {
      lit.quantity--;
      console.log("Value of " + lit.product.name + " after decrement ", lit.quantity);
      this.cartService.updateLineQuantity(lineId, lit.quantity);
    }
  }

  rowColor(color){
      document.body.style.background = color;
  }

  // getLinesWithNotAvailableProducts(): LineItem[]{
  //   for(let line of this.lineItems){
  //     for(let notAvProd of this.notAvailableProducts) {
  //       if (line.product.id === notAvProd.id){
  //         this.lineItemsWithNotAvailableProducts.push(line);
  //       }
  //         }
  //   }
  //   console.log("expected lines: " + this.lineItemsWithNotAvailableProducts)
  //   return this.lineItemsWithNotAvailableProducts;
  // }

  public addProductToWishList(idProd: number, idWish: number){
    return this.wishListService.addProductToWishList(idProd, idWish);
    console.log("product with id: " + idProd + " added");
  }

  public chooseNotification(idLineItem: number){

    let foundLineItem = this.lineItems.find((lineItem) => lineItem.id == idLineItem);
    console.log("notAvailableProducts are : " + this.notAvailableProducts);

    // let productsNotAv = this.cartService.checkNotAvailableProducts(this.cart);
    // console.log("productsNotAv are : " + this.notAvailableProducts);

    if(this.notAvailableProducts != null){
      this.notificationText = "Not enough products, available " + foundLineItem.product.quantity;
      return this.notificationText;
    }

  }

}

