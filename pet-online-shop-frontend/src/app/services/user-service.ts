import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {BehaviorSubject, Observable, of, Subject} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {url} from "../../environments/environment";
import {User} from '../model/User';
import {JwtResponse} from '../response/JwtResponse';
import {CreditCard} from "../model/credit-card";
import {Router} from "@angular/router";

@Injectable({providedIn: "root"})
export class UserService {

  id: number;

  public currentUserSubject: BehaviorSubject<JwtResponse>;
  public currentUser: Observable<JwtResponse>;
  public nameTerms = new Subject<string>();
  public name$ = this.nameTerms.asObservable();

  public isCreditCardAdded: boolean = false;

  constructor(private http: HttpClient,
              private cookieService: CookieService, public router: Router) {
    const memo = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<JwtResponse>(JSON.parse(memo));
    this.currentUser = this.currentUserSubject.asObservable();
    cookieService.set('currentUser', memo);
  }

  get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(loginRequest) {
    const signInUrl = `${url}/user/login`;
    return this.http.post<JwtResponse>(signInUrl, loginRequest).pipe(//FIXME BUG-001
      tap(u => {
        if (u && u.token) {
          this.cookieService.set('currentUser', JSON.stringify(u));
          console.log("cookie setup");
          localStorage.setItem('currentUser', JSON.stringify(u));
          console.log("storage setup");
          this.currentUserSubject.next(u);
          return u;
        }
      }),
      catchError(err => {
        console.log("PROBLEM WITH SIGN IN");
        console.log(err);
        return of(err)
      })
    );

  }

  signUp(user: User): Observable<User> {
    const signUpUrl = `${url}/user/register`;
    return this.http.post<User>(signUpUrl, user);
  }

  findUser(email: string): Observable<User> {
    const findUserUrl = `${url}/user/find-one-by/${email}`;
    return this.http.get<User>(findUserUrl);
  }

  findUserById(id: number): Observable<User> {
    const findUserByIdUrl = `${url}/user/find-by-id/${id}`;
    return this.http.get<User>(findUserByIdUrl);
  }

  updateUser(user: User): Observable<User> {
    const updateUserUrl = `${url}/user/update-user-put`;
    // user.password = user.newPasswordConfirmation;
    return this.http.put<User>(updateUserUrl, user);
  }

  findAllUsers(): Observable<User[]> {
    const findAllUsersUrl = `${url}/user/get-all`;
    return this.http.get<User[]>(findAllUsersUrl);
  }

  deleteUser(id: number): Observable<Object> {
    const deleteUserUrl = `${url}/user/delete/${id}`;
    return this.http.get(deleteUserUrl);
  }

  createCreditCard(card: CreditCard): Observable<CreditCard> {
    const createCreditCardUrl = `${url}/credit-card/add`;
    return this.http.post<CreditCard>(createCreditCardUrl, card);
  }

  logout (): Observable<any>{
    const logoutUrl = `${url}/user/logout`;
    localStorage.removeItem("currentUser");
    this.cookieService.deleteAll("/");
    return this.http.get<any>(logoutUrl);
  }

}
