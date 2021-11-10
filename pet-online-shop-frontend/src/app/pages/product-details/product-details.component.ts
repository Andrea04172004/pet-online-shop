import { Component, OnInit } from '@angular/core';
import {Product} from "../../model/Product";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../services/product-service";
import {toNumbers} from "@angular/compiler-cli/src/diagnostics/typescript_version";
import {CartService} from "../../services/cart-service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  public product: Product;
  public productId: string;
  public selectedImg: String;

  constructor(public productService: ProductService, public cartService: CartService,
              private route: ActivatedRoute, private router: Router) {
   this.productId = this.route.snapshot.paramMap.get('id');

   this.productService.findProductById(Number.parseFloat(this.productId)).subscribe((data)=>{
     this.product = data;
     this.selectedImg = data.image[0];

     console.log(data.image.length == 0);
     if(data.image.length == 0){
       this.product.image.push("https://www.ticketpro.by/storage/img/no-image.png");
       this.selectedImg = "https://www.ticketpro.by/storage/img/no-image.png";
     }
   });
  }

  ngOnInit(): void {
  }
  addProductToCard(productId: number) {
    console.log(productId);
    this.cartService.addProductToCart(productId);
  }

}
