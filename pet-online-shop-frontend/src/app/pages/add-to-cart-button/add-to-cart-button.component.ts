import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart-service";

@Component({
  selector: 'app-add-to-cart-button',
  templateUrl: './add-to-cart-button.component.html',
  styleUrls: ['./add-to-cart-button.component.css'],
  animations: [
    trigger('showDoneIcon', [
      state('show', style({
        opacity: 1
      })),
      state('hide', style({
        opacity: 0
      })),
      transition('show => hide', animate('600ms ease-out')),
      transition('hide => show', animate('1000ms ease-in'))
    ])
  ]

})
export class AddToCartButtonComponent implements OnInit {

  @Input() public product: Product;
  @Input() public added: boolean;
  @Output() newItemEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private cartService?: CartService) {

  }

  ngOnInit(): void {

    this.added = this.cartService.checkIfProductInCart(this.product.id);
  }

  onClick() {

    if (this.added) {
      this.added = false;
      this.cartService.deleteLineItemByProduct(this.product.id);

    } else {
      this.added = true;
      this.cartService.addProductToCart(this.product.id);
    }
    this.newItemEvent.emit(this.added);
  }
}
