import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user-service";
import {User} from "../../model/User";
import {ActivatedRoute} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Address} from "../../model/address";
import {AddAddressFormComponent} from "../add-address-form/add-address-form.component";
import {FormFieldCustomControlExampleComponent} from "../add-phone-form/form-field-custom-control-example.component";
import {Roles} from "../roles";

@Component({
  selector: 'app-edit-user-new',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {

  roles: Roles[] = [
    {value: 'ROLE_ADMIN', viewValue: 'ROLE_ADMIN'},
    {value: 'ROLE_MANAGER', viewValue: 'ROLE_MANAGER'},
    {value: 'ROLE_CUSTOMER', viewValue: 'ROLE_CUSTOMER'}
  ];


  public userForm: FormGroup;
  public user: User;
  public id: number;
  public preSelectedPhone: string;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<EditUserComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.user = new User();

    this.userForm = fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      phoneList: ['', Validators.required],
      addressList: ['', Validators.required],
      role: ['', Validators.required]
    })
  }

  ngOnInit(): void {

    this.id = this.data.userId;

    this.userService.findUserById(this.id).subscribe(u => {

      this.user = new User(u);

      this.preSelectedPhone = this.user.phoneList[0];

      this.userForm.patchValue({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email,
        phoneList: this.user.phoneList[0],
        addressList: this.user.addressDtoSet[0],
        role: this.user.role
      })
    })
  }


  onSubmit() {

    this.user.firstName = this.userForm.controls.firstName.value;
    this.user.lastName = this.userForm.controls.lastName.value;
    this.user.email = this.userForm.controls.email.value;

    this.userService.updateUser(this.user).subscribe((error) => {
      console.log(error);
    });

    this.onClose()
  }



  onClear() {
    this.userForm.reset();
  }

  onClose() {
    this.userForm.reset();
    this.dialogRef.close();
  }

  get phoneList() {
    return this.user.phoneList;
  }

  get addressList() {
    return this.user.addressDtoSet;
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

    this.userService.findUserById(this.id).subscribe((data)=>{
      this.user = data;
    })
  }

  onChangePhone() {

    this.userService.findUserById(this.id).subscribe((data)=>{
      this.user = data;
    })
  }

  onChangeRole() {
    this.userService.findUserById(this.id).subscribe((data)=>{
      this.user = data;
    })
  }
}


