import {Component, ViewChild} from '@angular/core';
import {UserRoles} from "./model/enums/user-roles.enum";
import {JwtResponse} from "./response/JwtResponse";
import {Router} from "@angular/router";
import {CartService} from "./services/cart-service";
import {UserService} from "./services/user-service";
import {HeaderMenuComponent} from "./pages/header-menu/header-menu.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'babyshop-frontend';


  public opened: boolean = false;

  public role = UserRoles;
  public currentUser: JwtResponse;
  public roles: UserRoles;


  constructor(private router: Router, public cartService: CartService,
              private userService: UserService) {
    this.currentUser = userService.currentUserValue;
  }

  public toggleSidebar() {
    this.opened = !this.opened;
  }
}
