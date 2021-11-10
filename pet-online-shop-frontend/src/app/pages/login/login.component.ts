import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginRequest} from '../../model/login-request';
import {UserService} from '../../services/user-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  public loginRequest: LoginRequest;
  public userName: string;
  public password: string;

  constructor(private userService: UserService,
              private router: ActivatedRoute,
              private route: Router) {
  }

  public ngOnInit() {

    this.loginRequest = new LoginRequest();

  }

  public onSubmit() {
    this.loginRequest.username = this.userName;
    this.loginRequest.password = this.password;

    this.userService.login(this.loginRequest).subscribe((user) => {
      if (user) {

        this.route.navigateByUrl('/user-details');
        console.log('welcome ' + user);
      }
    });
  }

  fillLoginFields(u, p) {
    this.userName = u;
    this.password = p;
    this.onSubmit();
  }

}
