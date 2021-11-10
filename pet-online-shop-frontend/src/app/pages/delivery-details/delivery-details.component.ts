import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../model/User";
import {UserService} from "../../services/user-service";
import {Address} from "../../model/address";
import {MatDialog} from "@angular/material/dialog";
import {AddAddressFormComponent} from "../add-address-form/add-address-form.component";
import {FormFieldCustomControlExampleComponent} from "../add-phone-form/form-field-custom-control-example.component";

@Component({
  selector: 'app-delivery-details',
  templateUrl: './delivery-details.component.html',
  styleUrls: ['./delivery-details.component.css']
})
export class DeliveryDetailsComponent implements OnInit {

  public deliveryForm: FormGroup;
  public user: User;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private dialog: MatDialog) {

    this.user = new User();


    this.deliveryForm = fb.group({
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      email: [this.user.email, Validators.required],
      phoneList: ['', Validators.required],
      addressList: ['', Validators.required],
    })

  }

  ngOnInit(): void {
    const account = this.userService.currentUserValue.account;

    this.userService.findUser(account).subscribe(u => {
      this.user = new User(u);

      this.deliveryForm.patchValue({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email
      });
    });
  }


  public getFormattedRow(address: Address): string {
    return "city: " + address.city + " street: " + address.street + " Building: " + address.buildingNumber + " Apartment: " + address.apartmentNumber;
  }

  onAddAddress() {
    this.dialog.open(AddAddressFormComponent);
  }

  onAddPhone() {
    this.dialog.open(FormFieldCustomControlExampleComponent);
  }

  onChangeAddress(){
    const account = this.userService.currentUserValue.account;

    this.userService.findUser(account).subscribe((data)=>{
      this.user = data;
    })
  }

  onChangePhone() {
    const account = this.userService.currentUserValue.account;

    this.userService.findUser(account).subscribe((data)=>{
      this.user = data;
    })
  }

}
