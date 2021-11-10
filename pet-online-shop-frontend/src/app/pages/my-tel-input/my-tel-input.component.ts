import {
  Component,
  ElementRef,
  EventEmitter,
  Inject,
  Input,
  OnDestroy,
  Optional,
  Output,
  Self,
  ViewChild
} from "@angular/core";
import {MAT_FORM_FIELD, MatFormField, MatFormFieldControl} from "@angular/material/form-field";
import {
  AbstractControl,
  ControlValueAccessor,
  FormBuilder,
  FormControl,
  FormGroup,
  NgControl,
  Validators
} from "@angular/forms";
import {Subject} from "rxjs";
import {FocusMonitor} from "@angular/cdk/a11y";
import {coerceBooleanProperty} from "@angular/cdk/coercion";
import {InjectorInstance} from "../../app.module";
import {HttpClient} from "@angular/common/http";
import {MyTel} from "../add-phone-form/form-field-custom-control-example.component";
import {url} from "../../../environments/environment";

@Component({
  selector: 'my-tel-input',
  templateUrl: 'my-tel-input.component.html',
  styleUrls: ['my-tel-input.component.css'],
  providers: [{provide: MatFormFieldControl, useExisting: MyTelInput}],
  host: {
    '[class.example-floating]': 'shouldLabelFloat',
    '[id]': 'id',
  }
})
export class MyTelInput
  implements ControlValueAccessor, MatFormFieldControl<MyTel>, OnDestroy {

  @ViewChild('area') areaInput: HTMLInputElement;
  @ViewChild('exchange') exchangeInput: HTMLInputElement;
  @ViewChild('subscriber') subscriberInput: HTMLInputElement;

  @Output() itemEvent: EventEmitter<AbstractControl> = new EventEmitter<AbstractControl>();

  private _placeholder: string;
  private _required = false;
  private _disabled = false;

  parts: FormGroup;
  stateChanges = new Subject<void>();
  focused = false;

  private static nextId = 0;
  id = `example-tel-input-${MyTelInput.nextId++}`;


  constructor(
    formBuilder: FormBuilder,
    private _focusMonitor: FocusMonitor,
    private _elementRef: ElementRef<HTMLElement>,
    @Optional() @Inject(MAT_FORM_FIELD) public _formField: MatFormField,
    @Optional() @Self() public ngControl: NgControl,
  ) {

    this.parts = formBuilder.group({
      area: [
        null,
        [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
      ],
      exchange: [
        null,
        [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
      ],
      subscriber: [
        null,
        [Validators.required, Validators.minLength(4), Validators.maxLength(4)]
      ]
    }, {
      validators: [this.phoneUniqueValidator]
    });

    _focusMonitor.monitor(_elementRef, true).subscribe(origin => {
      if (this.focused && !origin) {
        this.onTouched();
      }
      this.focused = !!origin;
      this.stateChanges.next();
    });

    if (this.ngControl != null) {
      this.ngControl.valueAccessor = this;
    }
  }


  onChange = (_: any) => {
  };
  onTouched = () => {
  };

  get empty() {
    const {
      value: {area, exchange, subscriber}
    } = this.parts;

    return !area && !exchange && !subscriber;
  }

  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }

  @Input()
  get placeholder(): string {
    return this._placeholder;
  }

  set placeholder(value: string) {
    this._placeholder = value;
    this.stateChanges.next();
  }


  @Input()
  get required(): boolean {
    return this._required;
  }

  set required(value: boolean) {
    this._required = coerceBooleanProperty(value);
    this.stateChanges.next();
  }


  @Input()
  get disabled(): boolean {
    return this._disabled;
  }

  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value);
    this._disabled ? this.parts.disable() : this.parts.enable();
    this.stateChanges.next();
  }


  @Input()
  get value(): MyTel | null {
    if (this.parts.valid) {
      const {
        value: {area, exchange, subscriber}
      } = this.parts;
      return new MyTel(area, exchange, subscriber);
    }
    return null;
  }

  set value(tel: MyTel | null) {
    const {area, exchange, subscriber} = tel || new MyTel('', '', '');
    this.parts.setValue({area, exchange, subscriber});
    this.stateChanges.next();
  }

  get errorState(): boolean {
    return this.parts.invalid && this.parts.dirty;
  }


  autoFocusNext(control: AbstractControl, nextElement?: HTMLInputElement): void {
    if (!control.errors && nextElement) {
      this._focusMonitor.focusVia(nextElement, 'program');
    }
  }

  autoFocusPrev(control: AbstractControl, prevElement: HTMLInputElement): void {
    if (control.value.length < 1) {
      this._focusMonitor.focusVia(prevElement, 'program');
    }
  }

  ngOnDestroy() {
    this.stateChanges.complete();
    this._focusMonitor.stopMonitoring(this._elementRef);
  }

  setDescribedByIds(ids: string[]) {
    const controlElement = this._elementRef.nativeElement
      .querySelector('.example-tel-input-container')!;
    controlElement.setAttribute('aria-describedby', ids.join(' '));
  }

  onContainerClick() {
    if (this.parts.controls.subscriber.valid) {
      this._focusMonitor.focusVia(this.subscriberInput, 'program');
    } else if (this.parts.controls.exchange.valid) {
      this._focusMonitor.focusVia(this.subscriberInput, 'program');
    } else if (this.parts.controls.area.valid) {
      this._focusMonitor.focusVia(this.exchangeInput, 'program');
    } else {
      this._focusMonitor.focusVia(this.areaInput, 'program');
    }
  }

  writeValue(tel: MyTel | null): void {
    this.value = tel;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  _handleInput(control: AbstractControl, nextElement?: HTMLInputElement): void {
    this.autoFocusNext(control, nextElement);
    this.onChange(this.value);
  }

  get subscriber() {
    return this.parts.get('subscriber') as FormControl;
  }

  public phoneUniqueValidator(control: AbstractControl): boolean {


    let httpClient = InjectorInstance.get<HttpClient>(HttpClient);
    let subscriber = control.get('subscriber');
    let phone: string = control.get('area').value + control.get('exchange').value + control.get('subscriber').value;

    if ((subscriber.value && subscriber.value.length == 4)) {

      httpClient.get(`${url}/user/check-if-phone-exist?phone=${phone}`)
        .subscribe((count: any) => {
          if (count > 0) {
            console.log("this.subscriber---> " + subscriber);
            subscriber.setErrors({notUnique: true});
            this.itemEvent.emit(subscriber);
          }
        }, error => {
          console.log("request error " + error);
        })
    }
    return true;
  }

}
