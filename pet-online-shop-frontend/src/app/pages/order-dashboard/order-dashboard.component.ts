import { Component, OnInit } from '@angular/core';
import * as feather from 'feather-icons';
import {OrderService} from "../../services/order-service";
import {Order} from "../../model/order";
import {OrderStatuses} from "../../model/enums/order-status.enum";
import {LineItem} from "../../model/line-item";
import {Category} from "../../model/Category";
import {Product} from "../../model/Product";
import {TooltipComponent} from "@angular/material/tooltip";

@Component({
  selector: 'app-order-dashboard',
  templateUrl: './order-dashboard.component.html',
  styleUrls: ['./order-dashboard.component.css']
})
export class OrderDashboardComponent implements OnInit {

  public orders: Order[];
  public order = new Order();
  public statuses = OrderStatuses;
  public keys = Object.keys;
  private lineItems: LineItem[] = [];

  constructor(public orderService: OrderService) {
    orderService.getAllOrders().subscribe((data)=>{
      this.orders = data;
      console.log(this.orders);
    });
  }

  ngOnInit(): void {
    feather.replace();
  }



    public getOrderByFind(id: number): Order {
      return this.orders.find((x) => x.id === id);
    }

  public update(): void{
    this.orderService.updateOrder(new Order(
      this.order.id, this.order.status, this.order.lineItemDtoSet, this.order.orderPrice, this.order.userShortResponseDto,
      this.order.created, this.order.addressDto, this.order.paymentsDto)).subscribe();
  }


  public updateList(id: number, property: string, event: any): void {
      this.order = this.getOrderByFind(id);
      this.changeData(event, property);

      this.update();
    }

  public deleteOrder(id: number): void {
    this.orderService.deleteOrder(id).subscribe((p) => {
      this.orders.splice(this.orders.findIndex((p) => p.id == id), 1);
    });
  }

  public changeData(event: any, property: string): void {
    if (property === 'orderStatus') {
      this.order.status = event.target.value;
    }
    if (property === 'lineItemDtoSet') {
      this.order.lineItemDtoSet = event.target.textContent;
    }
    if (property === 'orderPrice') {
      this.order.orderPrice = event.target.textContent;
    }
    if (property === 'userShortResponseDto') {
      this.order.userShortResponseDto = event.target.textContent;
    }
    if (property === 'created') {
      this.order.created = event.target.value;
    }
    if (property === 'address') {
      this.order.addressDto = event.target.value;
    }
    if(property === 'payments'){
      this.order.paymentsDto = event.target.value;
    }
    console.log(this.order.orderPrice);
  }
}
