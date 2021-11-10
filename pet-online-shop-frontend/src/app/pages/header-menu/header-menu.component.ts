import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Cart} from "../../model/Cart";
import {CartService} from "../../services/cart-service";
import {LineItem} from "../../model/line-item";
import {UserRoles} from "../../model/enums/user-roles.enum";
import {UserService} from "../../services/user-service";
import {JwtResponse} from "../../response/JwtResponse";
import {AppComponent} from "../../app.component";
import {User} from "../../model/User";
import get = Reflect.get;

@Component({
  selector: 'app-header-menu',
  templateUrl: './header-menu.component.html',
  // tslint:disable-next-line:object-literal-sort-keys
  styleUrls: ['./header-menu.component.css'],
})
export class HeaderMenuComponent implements OnInit {
  public cart: Cart;
  public lineItemDtoSet: LineItem[] = [];

  public cartPrice: number;
  public itemCount: number;

  public role = UserRoles;

  constructor(private router: Router, public cartService: CartService,
              private userService: UserService, private appComponent: AppComponent) {

  }

  public ngOnInit(): void {
  }

  get currentUser (): JwtResponse{
    return this.userService.currentUserValue;
  }

  public toggleSidebar() {
    this.appComponent.opened = ! this.appComponent.opened;
  }

  public logout(){
    this.userService.logout().subscribe((x)=>{
     return  this.router.navigate(["/"]);
    });
  }

  get cartLength() {
    return this.lineItemDtoSet.length;
  }

  get getCartPrice() {
    this.itemCount = 0;
    this.cartPrice = 0;

    this.lineItemDtoSet.forEach(l => {
      this.itemCount += l.quantity;
      this.cartPrice += (l.quantity * l.product.price);
    });
    return Math.round(this.cartPrice);
  }
}
