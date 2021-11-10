import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CartService} from "../../services/cart-service";

@Component({
  selector: 'app-full-checkout',
  templateUrl: './full-checkout.component.html',
  styleUrls: ['./full-checkout.component.css']
})
export class FullCheckoutComponent implements OnInit {
  firstStep: boolean = false;
  secondStep: boolean = false;
  thirdStep: boolean =true;
  fourthStep: boolean =false;

  constructor(private _formBuilder: FormBuilder, public cartService: CartService) {
  }


  ngOnInit(): void {
  }
}
