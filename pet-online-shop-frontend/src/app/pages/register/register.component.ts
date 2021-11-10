import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user-service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "../../validators/custom-validators";
import {User} from "../../model/User";
import {debounceTime, tap} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {NotificationServiceService} from "../../services/notification-service.service";
import {url} from "../../../environments/environment";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {



  public user: User;
  public registrationForm: FormGroup;

  public mask = {
    guide: false,
    showMask: true,
    mask: ['(', /\d/, /\d/, /\d/, ')', '-', /\d/, '-', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/]
  };


  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router,
              private httpClient: HttpClient,
              private notificationService: NotificationServiceService
  ) {
    this.registrationForm = this.createSignupForm();
    this.user = new User();
  }

  createSignupForm(): FormGroup {
    return this.fb.group(
      {
        email: ['', {validators: [Validators.email, Validators.required], updateOn: 'blur'}],

        phone: ['', {validators: [Validators.required, Validators.minLength(15),Validators.maxLength(15)], updateOn: 'blur'}],

            // CustomValidators.patternValidator(/^[0-9]*$/, {
            //   onlyNumbers: true
            // }),
            // CustomValidators.patternValidator(/^((\+38-?)|0)?[0-9]{10}$/, {
            //   numberLength: true
            // }),

        password: [
          null,
          Validators.compose([
            Validators.required,
            CustomValidators.patternValidator(/\d/, {
              hasNumber: true
            }),
            CustomValidators.patternValidator(/[A-Z]/, {
              hasCapitalCase: true
            }),
            CustomValidators.patternValidator(/[a-z]/, {
              hasSmallCase: true
            }),
            CustomValidators.patternValidator(
              /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/,
              {
                hasSpecialCharacters: true
              }
            ),
            Validators.minLength(8)
          ])
        ],
        confirmPassword: [null, Validators.compose([Validators.required])]
      },
      {
        validator: CustomValidators.passwordMatchValidator,
      }
    );
  }

  ngOnInit(): void {
    this.checkEmail();
    this.checkPhone();

  }


  onSubmit() {

    this.user.email = this.registrationForm.controls.email.value;
    this.user.password = this.registrationForm.controls.password.value;
    this.user.phone = this.registrationForm.controls.phone.value;

    //this.notificationService.success(':: Submitted successfully');

    this.userService.signUp(this.user).subscribe(u => {
      this.router.navigate(['/login'])
    }, error => {
      console.log(error);
    });

    console.log(this.user);

  }


  get email() {
    return this.registrationForm.get('email') as FormControl;
  }

  checkEmail() {


    this.email.valueChanges.pipe(
      debounceTime(500),
      tap(email => {
        if (email != '' && !this.email.invalid) {
          this.email.markAsPending();
        } else {
          this.email.setErrors({'invalid': true});
        }
      })
    ).subscribe(email => {

      this.httpClient.get(`${url}/user/check-if-email-exist?email=${email}`)
        .subscribe((users: any[]) => {
          if (users.length > 0) {
            this.email.markAsPending({onlySelf: false});
            this.email.setErrors({notUnique: true});
          } else {
            this.email.markAsPending({onlySelf: false});
            this.email.setErrors(null);
          }
        });
    });
  }


  get phone() {
    return this.registrationForm.get('phone') as FormControl;
  }

  checkPhone() {

    this.phone.valueChanges.pipe(
      debounceTime(500),
      tap(phone => {
        if (phone != '' && !this.phone.invalid) {
          this.phone.markAsPending();
        } else {
          this.phone.setErrors({'invalid': true});
        }
      })
    ).subscribe(phone => {
      this.httpClient.get(`${url}/user/check-if-phone-exist?phone=${phone}`)
        .subscribe((phones: any) => {
          if (phones > 0) {
            this.phone.markAsPending({onlySelf: false});
            this.phone.setErrors({notUnique: true});
          } else {
            this.phone.markAsPending({onlySelf: false});
            this.phone.setErrors(null);
          }
        });
      console.log("this.phone---> " + phone);
    });
  }


}







