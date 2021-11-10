import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserService} from "../services/user-service";
import {Injectable} from "@angular/core";


@Injectable(
  {
    providedIn: "root"
  }
)
export class JwtInterceptor implements HttpInterceptor{

  constructor(private userService: UserService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const currentUser = this.userService.currentUserSubject.value;
    if (currentUser && currentUser.token) {
      req = req.clone({
        setHeaders: {
          Authorization: `${currentUser.type} ${currentUser.token}`,
          'Content-Type': 'application/json'
        }
      });
    }
    return next.handle(req);
  }
}
