import {User} from "./User";

export class CreditCard {
  constructor(
    public id?: number,
    public cardNumber?: string,
    public expirationDate?: string,
    public cardType?: string,
    public userDto?: User
  ) {}
}
