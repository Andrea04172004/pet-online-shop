import { Injectable, EventEmitter } from '@angular/core';
import {Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EventEmitterService {

  invokeFirstComponentFunction = new EventEmitter();
  subsVar: Subscription;

  constructor() { }

  onAllProductComponentButtonClick() {
    console.log("there----> eventEmmiter");
    this.invokeFirstComponentFunction.emit();
  }
}
