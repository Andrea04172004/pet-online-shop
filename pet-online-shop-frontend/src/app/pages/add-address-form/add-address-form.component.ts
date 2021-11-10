import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../model/User";
import {Address} from "../../model/address";
import {UserService} from "../../services/user-service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {NotificationServiceService} from "../../services/notification-service.service";

@Component({
  selector: 'app-add-address-form-simple',
  templateUrl: './add-address-form.component.html',
  styleUrls: ['./add-address-form.component.css']
})
export class AddAddressFormComponent implements OnInit {

  public addressForm: FormGroup;
  public user: User;
  public address: Address;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<Address>,
              private notificationService: NotificationServiceService) {
    this.user = new User();
    this.address = new Address();

    this.addressForm = this.fb.group({
      city: ['', Validators.required],
      street: ['', Validators.required],
      buildingNumber: ['', Validators.required],
      apartmentNumber: ['', Validators.required],
    })

  }

  ngOnInit(): void {

    const account = this.userService.currentUserValue.account;

    this.userService.findUser(account).subscribe(u => {
      this.user = new User(u);

    })

  }

  onSubmit() {
    this.address = new Address(0,
      this.addressForm.controls.city.value,
      this.addressForm.controls.street.value,
      this.addressForm.controls.buildingNumber.value,
      this.addressForm.controls.apartmentNumber.value);

    this.user.addressDtoSet.push(this.address);

    this.userService.updateUser(this.user).subscribe((error) => {
      console.log(error);
    });
    this.notificationService.success(':: Submitted successfully');
    this.onClose();
  }

  onClear() {
    this.addressForm.reset();
  }

  onClose() {
    this.addressForm.reset();
    this.dialogRef.close();
  }


}
