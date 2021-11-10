import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {UserRoles} from "../../model/enums/user-roles.enum";
import {CartService} from "../../services/cart-service";
import {UserService} from "../../services/user-service";
import {JwtResponse} from "../../response/JwtResponse";

@Component({
  selector: 'app-side-navbar',
  templateUrl: './side-navbar.component.html',
  styleUrls: ['./side-navbar.component.css']
})
export class SideNavbarComponent implements OnInit {
  mode = new FormControl('over');

  public role = UserRoles;
  // public currentUser: JwtResponse;

  constructor(private router: Router, public cartService: CartService,
              private userService: UserService, private appComponent: AppComponent) {
    // this.currentUser = this.userService.currentUserValue;
  }

  ngOnInit(): void {
  }
  public toggleSidebar() {
    this.appComponent.opened = ! this.appComponent.opened;
  }

  get currentUser (): JwtResponse{
    return this.userService.currentUserValue;
  }


}
