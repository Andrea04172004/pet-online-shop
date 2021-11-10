import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {Address} from "../../model/address";
import {UserService} from "../../services/user-service";
import {ActivatedRoute, Router} from "@angular/router";
import {CreditCard} from "../../model/credit-card";
import {Order} from "../../model/order";
import {OrderService} from "../../services/order-service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})

export class UserDetailsComponent implements OnInit {

  public user: User;
  public userId: string;
  public address: Address[];
  public phoneList: string[];
  public users: User[] = [];
  public creditCards: CreditCard[];
  public orders: Order[];


  constructor(public userService: UserService, private route: ActivatedRoute, private router: Router) {
    this.waitForOneSecond().then(value => {console.log('Im here')});
    this.userId = this.route.snapshot.paramMap.get('id');

    if (this.userId != null) {
      this.userService.findUserById(Number.parseFloat(this.userId)).subscribe((data) => {
        this.user = data;
        this.address = data.addressDtoSet;
        this.phoneList = data.phoneList;
        this.creditCards = data.creditCardDto;
        this.orders = data.orders;
        console.log(this.creditCards);
      })
    } else {
      const account = this.userService.currentUserValue.account;
      this.userService.findUser(account).subscribe(u => {
        this.user = new User(u);
      })
    }
  }


  ngOnInit(): void {
    // this.waitForOneSecond().then(
    //   (value) =>
    //     console.log("2 - " + this.user.phoneList)
    // );
  }

  public updateList(id: number, property: string, event: any): void {
    this.user = this.getUserByFind(id);
    console.log(this.user);
    this.changeData(event, property);
    this.update();
  }

  public getUserByFind(id: number): User {
    return this.users.find((x) => x.id === id);
  }


  public changeData(event: any, property: string): void {
    if (property === 'email') {
      this.user.email = event.target.textContent;
    }
    if (property === 'firstName') {
      this.user.firstName = event.target.textContent;
    }
    if (property === 'lastName') {
      this.user.lastName = event.target.textContent;
    }
    if (property === 'addressDtoSet') {
      this.user.addressDtoSet = event.target.textContent;
    }
    if (property === 'phoneList') {
      this.user.phoneList = event.target.textContent;
    }
    if (property === 'phone') {
      this.user.phone = event.target.textContent;
    }
    if (property === 'created') {
      this.user.created = event.target.textContent;
    }
    if (property === 'updated') {
      this.user.updated = event.target.textContent;
    }
    if (property === 'lastVisit') {
      this.user.lastVisit = event.target.textContent;
    }
    if (property === 'role') {
      this.user.role = event.target.textContent;
    }
    if (property === 'active') {
      this.user.active = event.target.textContent;
    }
    if (property === 'creditCardDto') {
      this.user.active = event.target.textContent;
    }
    if (property === 'orders') {
      this.user.active = event.target.textContent;
    }
  }

  public update(): void {
    // @ts-ignore
    this.userService.updateUser(new User(this.user.id, this.user.email, this.user.firstName, this.user.lastName,
      this.user.addressDtoSet, this.user.phoneList, this.user.phone, this.user.created, this.user.updated,
      this.user.lastVisit, this.user.role, this.user.active, this.user.creditCardDto, this.user.orders)).subscribe();
  }


  public getAllPhoneNumbers(): String[] {
    return this.phoneList;
  }

  public getAllAddresses(): Address[] {
    return this.address;
  }

  public getOrderById(id: number): Order {
    return this.orders.find((x) => x.id === id);
  }

  public getOrderAddress(orderId: number): Address {
    //return this.orders[orderId].address;
    return this.getOrderById(orderId).addressDto;
  }

  public waitForOneSecond() {
    return new Promise(resolve => {
      setTimeout(() => {
        resolve("I promise to return after one second!");
      }, 1000);
    });
  }
}

//   id: number;
//   email: string;
//   password: string;
//   passwordConfirmation: string; // register
//   newPassword: string; // update
//   newPasswordConfirmation: string; // update
//   firstName: string;
//   lastName: string;
//   addressDtoSet: Address[];
//   phoneList: string[];
//   phone: string; ?
//   created: string;
//   updated: string;
//   lastVisit: string;
//   role: string;
//   active: boolean;

//BACK

//private int id;
//    private String email;
//    private String password;
//    private String firstName;
//    private String lastName;
//Set<AddressEntity> addressEntitySet;
//List<String> phoneList = new ArrayList<>();
//    private LocalDate created;
//    private LocalDate updated;
//    private LocalDate lastVisit;
//    private String role;
//boolean active;
//CartEntity cartEntity; ?
//Set<OrderEntity> orderEntitySet; ?
//Set<CreditCardEntity> creditCardEntitySet; ?
