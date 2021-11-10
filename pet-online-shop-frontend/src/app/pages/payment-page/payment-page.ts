import {Component, OnInit,} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {User} from "../../model/User";
import {UserService} from "../../services/user-service";
import {CreditCard} from "../../model/credit-card";
import {FullCheckoutComponent} from "../full-checkout/full-checkout.component";

@Component({
  selector: 'app-payment-component',
  templateUrl: './payment-page.html',
  styleUrls: ['./payment-page.css']
})
export class PaymentPage implements OnInit {
  public paymentMethod: string;
  public methods: string[] = ['Cash', 'Credit card'];

  public user: User;
  public creditCards: CreditCard[];
  public addCreditCard: boolean = false;


  cardControl = new FormControl('', Validators.required);
  selectFormControl = new FormControl('', Validators.required);


  constructor(public userService: UserService, private fullCheck: FullCheckoutComponent) {
  }


  ngOnInit(): void {
    const account = this.userService.currentUserValue.account;

    this.userService.findUser(account).subscribe((data)=>{
      this.user = data;
      this.creditCards = this.user.creditCardDto;
    })
  }

   onChange(event){
     const account = this.userService.currentUserValue.account;

     this.userService.findUser(account).subscribe((data)=>{
       this.user = data;
       this.creditCards = this.user.creditCardDto;
     })
   }

   onSelect(cardNumb: string){
    if(cardNumb!= null){
     this.fullCheck.secondStep = true;
    }
   }
   onSelectMethod(method: string){
     if(method== 'Cash'){
       this.fullCheck.secondStep = true;
     }
   }



}
