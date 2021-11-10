import {Component, HostListener, Input, OnInit} from '@angular/core';
import {CreditCard} from "../../model/credit-card";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomValidators} from "../../validators/custom-validators";
import {UserService} from "../../services/user-service";
import {PaymentPage} from "../payment-page/payment-page";

@Component({
  selector: 'app-billing-details',
  templateUrl: './billing-details.component.html',
  styleUrls: ['./billing-details.component.css']
})
export class BillingDetailsComponent implements OnInit {

  public creditCard: CreditCard;
  public billingForm: FormGroup;

  public month: string;
  public year: string;

  public isAdded: boolean = false;

  constructor(private fb: FormBuilder,
              private router: Router,
              private userService: UserService) {
    this.creditCard = new CreditCard();
    this.billingForm = this.createBillingForm();
  }



  createBillingForm(): FormGroup {
    return this.fb.group(
      {
        cardHolderName: [
          null,
          Validators.compose([Validators.required])
        ],
        cardNumber: [
          null,
          Validators.compose([
            Validators.required,
            CustomValidators.patternValidator(/^[0-9]*$/, {
              onlyNumbersCard: true
            }),
            CustomValidators.patternValidator(/^((\+38-?)|0)?[0-9]{16}$/, {
              numberLengthCard: true
            }),
          ])
        ],
        expirationMonth: [
          null,
          Validators.compose([
            Validators.required,
            CustomValidators.patternValidator(/^((\+38-?)|0)?[0-9]{2}$/, {
              numberLengthExpMonth: true
            }),
            CustomValidators.patternValidator(/^[0-9]*$/, {
              onlyNumbersExpMonth: true
            }),
          ])
        ],
        expirationYear: [
          null,
          Validators.compose([
            Validators.required,
            CustomValidators.patternValidator(/^[0-9]*$/, {
              onlyNumbersExpYear: true
            }),
            CustomValidators.patternValidator(/^((\+38-?)|0)?[0-9]{4}$/, {
              numberLengthExpYear: true
            }),
          ])
        ],
        cvv: [
          null,
          Validators.compose([
            Validators.required,
            CustomValidators.patternValidator(/^[0-9]*$/, {
              onlyNumbersCVV: true
            }),
            CustomValidators.patternValidator(/^((\+38-?)|0)?[0-9]{3}$/, {
              numberLengthCVV: true
            }),
          ])
        ],
      }
    )

  }


  ngOnInit(): void {
  }

  onSubmit() {
    this.creditCard.expirationDate = this.month + "/" + this.year;
    this.userService.createCreditCard(this.creditCard).subscribe((data) => {
      this.userService.isCreditCardAdded = data != null;
    });

  }


}
