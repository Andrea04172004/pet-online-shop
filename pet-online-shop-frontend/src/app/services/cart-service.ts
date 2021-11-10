import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Product} from "../model/Product";
import {url} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Cart} from "../model/Cart";
import {LineItem} from "../model/line-item";


@Injectable({providedIn: 'root'})
export class CartService {

  public cart: Cart;
  public lineItem: LineItem;

  constructor(private http: HttpClient) {

  }

  public init(): void {
    if (!this.cart) {
      console.log("Before constructor");
      this.getUserCart().toPromise().then((shopCart)=>{
        this.cart = shopCart;
        console.log("Inside constructor");
      })
      // this.getUserCart().subscribe(resp => {//TODO Change to promise
      //   this.cart = resp;
      //   console.log("Inside constructor");
      // });
      console.log("After constructor");
    }

  }


  public addProductToCart(id: number): void {
    console.log("product----> " + id);
    const addProductToCart = `${url}/cart/add-line-item/${id}`;
    this.http.get(addProductToCart).subscribe(res => {
      console.log("res--> " + res);
      this.lineItem = new LineItem();
      this.lineItem = res;
      console.log("this.lineItem.id---> " + this.lineItem.id + " this.lineItem.product--> " + this.lineItem.product + " this.lineItem.quantity---> " + this.lineItem.quantity);
      this.cart.lineItemDtoSet.push(this.lineItem);
      console.log("this.cart.lineItemDtoSet.length---> " + this.cart.lineItemDtoSet.length);
    })
  };


  public deleteLineItemByLine(id: number): void {
    const deleteLineByLineItemUrl = `${url}/cart/delete-by-line/${id}`;

    this.http.delete(deleteLineByLineItemUrl).subscribe(res => {

      const index = this.cart.lineItemDtoSet.indexOf(res);

      console.log("before this.cart.lineItemDtoSet.length---> " + this.cart.lineItemDtoSet.length);
      this.cart.lineItemDtoSet.splice(index, 1);

      console.log("after this.cart.lineItemDtoSet.length---> " + this.cart.lineItemDtoSet.length);
    });

  }

  public deleteLineItemByProduct(id: number): void {
    const deleteLineByProductUrl = `${url}/cart/delete-by-product/${id}`;
    this.http.delete(deleteLineByProductUrl).subscribe(res => {
      console.log("res---> " + res);

      const index = this.cart.lineItemDtoSet.indexOf(res);

      console.log("before this.cart.lineItemDtoSet.length---> " + this.cart.lineItemDtoSet.length);
      this.cart.lineItemDtoSet.splice(index, 1);

      console.log("after this.cart.lineItemDtoSet.length---> " + this.cart.lineItemDtoSet.length);

    });
  }


  public getUserCart():Observable<Cart>{
     const getUserCartUrl = `${url}/cart/getCart`;
     return this.http.get<Cart>(getUserCartUrl);
  }

  public updateLineQuantity(lineId: number, quantity: number): void {
    const updateLineQuantity = `${url}/cart/updateLine/${lineId}`;
    this.http.put<LineItem>(updateLineQuantity, quantity).subscribe();
  }

  public checkCartQuantity(cart: Cart) {
    const quantityCheck = `${url}/order/checkQuantity`;
    console.log(cart);
    return this.http.post(quantityCheck, cart, {responseType: 'text'});
  }


  public checkNotAvailableProducts(cart: Cart): Observable<Product[]> {
    console.log(cart);
    const notAvailableProductsUrl = `${url}/cart/getNotAvailableProducts`;
    return this.http.post<Product[]>(notAvailableProductsUrl, cart);
  }

  public deleteLineByProductId(id: number): void {
    const deleteLineItemUrl = `${url}/cart/deleteLineByProductId/${id}`;
    this.http.delete(deleteLineItemUrl).subscribe();
  }


  public checkIfProductInCart(id: number): boolean {
    this.init();

    let status: boolean;
    status = false;

    console.log(this.cart);
    console.log(this.cart.lineItemDtoSet);
    this.cart.lineItemDtoSet.forEach(item => {
      if (item.product.id == id) status = true;
    });
    return status;
  }


}
