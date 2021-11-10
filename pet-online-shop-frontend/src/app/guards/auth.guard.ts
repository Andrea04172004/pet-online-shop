import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from "../services/user-service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router,
              private userService: UserService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const currentUser = this.userService.currentUserValue;

    if(currentUser){
      if(route.data.role && route.data.role.indexOf(currentUser.role) === -1){
        this.router.navigate(['/']);
        return false;
      }
      return  true;
    }
    this.router.navigate(['/login'],{queryParams: {returnUrl: state.url}});
    return false;
  }
}

