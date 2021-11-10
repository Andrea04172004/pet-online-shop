import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {getValidationConfigFromCardNo} from "./card.helper";
import {luhnValidator} from "./luhnValidator";

@Component({
  selector: 'app-luhn-test',
  templateUrl: './luhn-test.component.html',
  styleUrls: ['./luhn-test.component.scss']
})
export class LuhnTestComponent implements OnInit {

  cardNumberGroup: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.cardNumberGroup = new FormGroup({
      cardNumber: new FormControl('', [
        Validators.required,
        Validators.minLength(12),
        luhnValidator()
      ])
    })
  }
  getCardNumberControl(): AbstractControl | null {
    return this.cardNumberGroup && this.cardNumberGroup.get('cardNumber');
  }

  cardMaskFunction(rawValue: string): Array<RegExp> {
    const card = getValidationConfigFromCardNo(rawValue);
    if (card) {
      return card.mask;
    }
    return [/\d/];
  }
}
