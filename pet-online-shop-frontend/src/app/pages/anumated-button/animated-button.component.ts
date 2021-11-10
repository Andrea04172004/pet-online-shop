import {Component, Input, OnInit} from '@angular/core';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';
import {
  faCartPlus,
  faShoppingCart
} from "@fortawesome/free-solid-svg-icons";

import * as far from "@fortawesome/free-regular-svg-icons";
import * as fas from "@fortawesome/free-solid-svg-icons";
import {CartService} from "../../services/cart-service";
import {Cart} from "../../model/Cart";
import {LineItem} from "../../model/line-item";
import {ThemePalette} from '@angular/material/core';

export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}
@Component({
  selector: 'app-animated-button',
  templateUrl: './animated-button.component.html',
  styleUrls: ['./animated-button.component.css'],
  animations: [
    trigger('buttonTextStateTrigger', [
      state('shown', style({
        opacity: 5
      })),
      state('transitioning', style({
        opacity: 0.3
      })),
      transition('shown => transitioning', animate('300ms ease-out')),
      transition('transitioning => shown', animate('300ms ease-in'))
    ])
  ]
})
export class AnimatedButtonComponent  {
  cartIcons = [fas.faShoppingCart, ]
  wishIcons = [fas.faHeart, far.faHeart]

  cartButtonTextState = 'shown';

  cartButtonText = faShoppingCart;

  transitionCartButtonText = faShoppingCart;


  wishButtonTextState = 'shown';
  wishButtonText = this.wishIcons[1];
  transitionWishButtonText = this.wishIcons[1];

  /**
   * Respond to the transition event of the button text
   * by setting the text awaiting transition then setting the state as shown
   */
  buttonTextTransitioned(event) {
    this.cartButtonText = this.transitionCartButtonText;
    this.cartButtonTextState = this.cartButtonTextState = 'shown';
  }

  wishButtonTextTransitioned(event) {
    this.wishButtonText = this.transitionWishButtonText;
    this.wishButtonTextState = this.wishButtonTextState = 'shown';
  }

  onAddToCart() {
    console.log("Btn add")
    // Kick off the first transition
    this.cartButtonTextState = 'transitioning';
    this.transitionCartButtonText = faCartPlus;


    // Do whatever logic here. If it is asynchronous, put the remaining code in your subscribe/then callbacks
    // Note if your logic is snappy, you could leave the timeouts in to simulate the animation for a better UX

    // setTimeout(() => {
    //   this.cartButtonTextState = 'transitioning';
    //   this.transitionCartButtonText = faShoppingCart;
    // }, 1800);

  }
  onAddToWishList(){
    this.wishButtonTextState = 'transitioning';
    this.transitionWishButtonText = this.wishIcons[0];
  }


  task: Task = {
    name: 'Indeterminate',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Primary', completed: false, color: 'primary'},
      {name: 'Accent', completed: false, color: 'accent'},
      {name: 'Warn', completed: false, color: 'warn'}
    ]
  };

  allComplete: boolean = false;

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
  }
}
