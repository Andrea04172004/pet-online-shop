import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../model/order";
import {url} from "../../environments/environment";
import {Product} from "../model/Product";
import {Category} from "../model/Category";

@Injectable({providedIn: 'root'})
export class OrderService {

  public tempOrder: Order = new Order();

  constructor(private http: HttpClient) {

  }


  public saveOrder(): Observable<Order>{
    const saveOrderUrl = `${url}/order/make`;
    return this.http.get<Order>(saveOrderUrl);
  }

  public getAllOrders (): Observable<Order[]>{
    console.log("inside service");
    const getAllOrders = `${url}/order/getAllOrders`;
    return this.http.get<Order[]>(getAllOrders);
  }

  public findOrderById (id: number): Observable<Order>{
    const findOrderById = `${url}/order/get-by${id}`;
    return this.http.get<Order>(findOrderById);
  }

  public updateOrder(order: Order): Observable<Order>{

    const updateOrderUrl = `${url}/order/updateOrder`;

    return this.http.put<Order>(updateOrderUrl, order);
  }

  public deleteOrder(id: number): Observable<Order>{
    const deleteOrderUrl = `${url}/order/deleteOrder/${id}`;
   return this.http.delete<Order>(deleteOrderUrl);
  }


}
