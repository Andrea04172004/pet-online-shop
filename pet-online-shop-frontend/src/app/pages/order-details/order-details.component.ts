import {Component, Injectable, OnInit} from '@angular/core';
import {OrderService} from "../../services/order-service";
import {ActivatedRoute, Router} from "@angular/router";
import {Order} from "../../model/order";
import {OrderStatuses} from "../../model/enums/order-status.enum";
import {Address} from "../../model/address";
import {Payment} from "../../model/payment";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  public order: Order;
  public orderId: string;
  public orderStatus: OrderStatuses;
  public payments: Payment[] = [];

  constructor(public orderService: OrderService,public route: ActivatedRoute) {
    this.orderId = this.route.snapshot.paramMap.get('id');

    this.orderService.findOrderById(Number.parseFloat(this.orderId)).subscribe((data)=>{
      this.order = data;
      this.orderStatus = data.status;
    })


  }

  ngOnInit(): void {
  }

}
