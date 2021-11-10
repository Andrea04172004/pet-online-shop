import {Address} from "./address";
import {CreditCard} from "./credit-card";
import {Order} from "./order";

export class User {

  id: number;
  email: string;
  password: string;
  passwordConfirmation: string;
  newPassword: string;
  newPasswordConfirmation: string;
  firstName: string;
  lastName: string;
  addressDtoSet: Address[];
  phoneList: string[];
  phone: string;
  created: string;
  updated: string;
  lastVisit: string;
  role: string;
  active: boolean;
  creditCardDto: CreditCard[];
  orders: Order[];

  constructor(user?: User) {
    if (user) {
      console.log("inside " + user);
      this.id = user.id;
      this.firstName = user.firstName;
      this.lastName = user.lastName;
      this.email = user.email;
      this.password = user.password;
      this.active = user.active;
      this.role = user.role;
      this.passwordConfirmation = user.passwordConfirmation;
      this.newPassword = user.newPassword;
      this.newPasswordConfirmation = user.passwordConfirmation;
      this.addressDtoSet = user.addressDtoSet;
      this.phoneList = user.phoneList;
      this.phone = user.phone;
      this.created = user.created;
      this.updated = user.updated;
      this.lastVisit = user.lastVisit;
      this.creditCardDto = user.creditCardDto;
      this.orders = user.orders;
    }
  }

}
