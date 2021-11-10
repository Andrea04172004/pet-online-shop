import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup} from '@angular/forms';
import {User} from "../../model/User";
import {UserService} from "../../services/user-service";
import {HttpClient} from "@angular/common/http";
import {MatDialogRef} from "@angular/material/dialog";
import {NotificationServiceService} from "../../services/notification-service.service";

@Component({
  selector: 'app-form-field-custom-control-example',
  templateUrl: './form-field-custom-control-example.component.html',

})
export class FormFieldCustomControlExampleComponent implements OnInit {

  public user: User;
  public currentPhone: MyTel;

  form: FormGroup = new FormGroup({
    tel: new FormControl(new MyTel('', '', ''), {}),
  });

  constructor(private userService: UserService,
              private httpClient: HttpClient,
              private dialogRef: MatDialogRef<FormFieldCustomControlExampleComponent>,
              private notificationService: NotificationServiceService) {
    this.user = new User();
  }

  ngOnInit(): void {
    const account = this.userService.currentUserValue.account;
    this.userService.findUser(account).subscribe(u => {
      this.user = new User(u);
    })
  }

  onSubmit() {

    this.currentPhone = this.form.controls.tel.value;
    console.log(this.currentPhone);
    this.user.phoneList.push(this.currentPhone.mergePhone());

    this.userService.updateUser(this.user).subscribe((error) => {
      console.log(error);
    });
    this.notificationService.success(':: Submitted successfully');
    this.onClose();
  }

  onClear() {
    this.tel.reset();
  }

  onClose() {
    this.tel.reset();
    this.dialogRef.close();
  }

  get tel() {
    return this.form.get('tel') as FormControl;
  }

  getError(control: AbstractControl) {
    console.log("control.get('subscriber').errors---> " + control.get('subscriber').errors);
    return control.get('subscriber').errors;
  }

}

export class MyTel {
  constructor(
    public area?: string,
    public exchange?: string,
    public subscriber?: string
  ) {
  }

  mergePhone(): string {
    return this.area + this.exchange + this.subscriber;
  }

}

//
//
// @Component({
//   selector: 'example-tel-input',
//   templateUrl: 'example-tel-input-example.html',
//   styleUrls: ['example-tel-input-example.css'],
//   providers: [{provide: MatFormFieldControl, useExisting: MyTelInput}],
//   host: {
//     '[class.example-floating]': 'shouldLabelFloat',
//     '[id]': 'id',
//   }
// })
// export class MyTelInput
//   implements ControlValueAccessor, MatFormFieldControl<MyTel>, OnDestroy {
//
//   @ViewChild('area') areaInput: HTMLInputElement;
//   @ViewChild('exchange') exchangeInput: HTMLInputElement;
//   @ViewChild('subscriber') subscriberInput: HTMLInputElement;
//   @ViewChild(FormFieldCustomControlExampleComponent)
//   telephone: FormFieldCustomControlExampleComponent;
//
//   @Output() itemEvent: EventEmitter<FormControl> = new EventEmitter<FormControl>();
//
//   private _placeholder: string;
//   private _required = false;
//   private _disabled = false;
//
//   parts: FormGroup;
//   stateChanges = new Subject<void>();
//   focused = false;
//
//   public mergeParts(): string {
//     return this.parts.get('area').value + this.parts.get('exchange').value + this.parts.get('subscriber').value;
//   }
//
//   private static nextId = 0;
//   id = `example-tel-input-${MyTelInput.nextId++}`;
//
//
//   constructor(
//     formBuilder: FormBuilder,
//     private _focusMonitor: FocusMonitor,
//     private _elementRef: ElementRef<HTMLElement>,
//     @Optional() @Inject(MAT_FORM_FIELD) public _formField: MatFormField,
//     @Optional() @Self() public ngControl: NgControl,
//   ) {
//
//     this.parts = formBuilder.group({
//       area: [
//         null,
//         [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
//       ],
//       exchange: [
//         null,
//         [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
//       ],
//       subscriber: [
//         null,
//         [Validators.required, Validators.minLength(4), Validators.maxLength(4)]
//       ]
//     }, {
//       validators: [this.phoneUniqueValidator]
//     });
//
//     _focusMonitor.monitor(_elementRef, true).subscribe(origin => {
//       if (this.focused && !origin) {
//         this.onTouched();
//       }
//       this.focused = !!origin;
//       this.stateChanges.next();
//     });
//
//     if (this.ngControl != null) {
//       this.ngControl.valueAccessor = this;
//     }
//   }
//
//
//   onChange = (_: any) => {
//   };
//   onTouched = () => {
//   };
//
//   get empty() {
//     const {
//       value: {area, exchange, subscriber}
//     } = this.parts;
//
//     return !area && !exchange && !subscriber;
//   }
//
//   get shouldLabelFloat() {
//     return this.focused || !this.empty;
//   }
//
//   // @Input('aria-describedby') userAriaDescribedBy: string;
//
//   @Input()
//   get placeholder(): string {
//     return this._placeholder;
//   }
//
//   set placeholder(value: string) {
//     this._placeholder = value;
//     this.stateChanges.next();
//   }
//
//
//   @Input()
//   get required(): boolean {
//     return this._required;
//   }
//
//   set required(value: boolean) {
//     this._required = coerceBooleanProperty(value);
//     this.stateChanges.next();
//   }
//
//
//   @Input()
//   get disabled(): boolean {
//     return this._disabled;
//   }
//
//   set disabled(value: boolean) {
//     this._disabled = coerceBooleanProperty(value);
//     this._disabled ? this.parts.disable() : this.parts.enable();
//     this.stateChanges.next();
//   }
//
//
//   @Input()
//   get value(): MyTel | null {
//     if (this.parts.valid) {
//       const {
//         value: {area, exchange, subscriber}
//       } = this.parts;
//       return new MyTel(area, exchange, subscriber);
//     }
//     return null;
//   }
//
//   set value(tel: MyTel | null) {
//     const {area, exchange, subscriber} = tel || new MyTel('', '', '');
//     this.parts.setValue({area, exchange, subscriber});
//     this.stateChanges.next();
//   }
//
//   get errorState(): boolean {
//     return this.parts.invalid && this.parts.dirty;
//   }
//
//
//   autoFocusNext(control: AbstractControl, nextElement?: HTMLInputElement): void {
//     if (!control.errors && nextElement) {
//       this._focusMonitor.focusVia(nextElement, 'program');
//     }
//   }
//
//   autoFocusPrev(control: AbstractControl, prevElement: HTMLInputElement): void {
//     if (control.value.length < 1) {
//       this._focusMonitor.focusVia(prevElement, 'program');
//     }
//   }
//
//   ngOnDestroy() {
//     this.stateChanges.complete();
//     this._focusMonitor.stopMonitoring(this._elementRef);
//   }
//
//   setDescribedByIds(ids: string[]) {
//     const controlElement = this._elementRef.nativeElement
//       .querySelector('.example-tel-input-container')!;
//     controlElement.setAttribute('aria-describedby', ids.join(' '));
//   }
//
//   onContainerClick() {
//     if (this.parts.controls.subscriber.valid) {
//       this._focusMonitor.focusVia(this.subscriberInput, 'program');
//     } else if (this.parts.controls.exchange.valid) {
//       this._focusMonitor.focusVia(this.subscriberInput, 'program');
//     } else if (this.parts.controls.area.valid) {
//       this._focusMonitor.focusVia(this.exchangeInput, 'program');
//     } else {
//       this._focusMonitor.focusVia(this.areaInput, 'program');
//     }
//   }
//
//   writeValue(tel: MyTel | null): void {
//     this.value = tel;
//   }
//
//   registerOnChange(fn: any): void {
//     this.onChange = fn;
//   }
//
//   registerOnTouched(fn: any): void {
//     this.onTouched = fn;
//   }
//
//   setDisabledState(isDisabled: boolean): void {
//     this.disabled = isDisabled;
//   }
//
//   _handleInput(control: AbstractControl, nextElement?: HTMLInputElement): void {
//     this.autoFocusNext(control, nextElement);
//     this.onChange(this.value);
//   }
//
//    get subscriber() {
//     return this.parts.get('subscriber') as FormControl;
//    }
//
//   public phoneUniqueValidator(control: AbstractControl): boolean {
//
//
//     let httpClient = InjectorInstance.get<HttpClient>(HttpClient);
//     let subscriber = control.get('subscriber').value;
//     let phone: string = control.get('area').value + control.get('exchange').value + control.get('subscriber').value;
//     if ((subscriber && subscriber.length == 4)) {
//
//       httpClient.get(`http://localhost:8080/api/user/check-if-phone-exist?phone=${phone}`)
//         .subscribe((count: any) => {
//           if (count > 0) {
//             this.subscriber.setErrors({notUnique: true});
//             this.itemEvent.emit(this.subscriber);
//           }
//         }, error => {
//           console.log("request error " + error);
//         })
//     }
//     return true;
//   }
//
// }
//
//
//
