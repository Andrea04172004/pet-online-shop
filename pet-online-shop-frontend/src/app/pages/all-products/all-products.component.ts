import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../services/product-service";
import {CategoryService} from "../../services/category-service";
import {Product} from "../../model/Product";
import {Category} from "../../model/Category";
import {Cart} from "../../model/Cart";
import {CartService} from "../../services/cart-service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {AddProductToWishListDialogComponent} from "../add-product-to-wish-list-dialog/add-product-to-wish-list-dialog.component";
import {WishlistService} from "../../services/wishlist-service";
import {EventEmitterService} from "../../services/event-emitter.service";


@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.css'],
})
export class AllProductsComponent implements OnInit {

  private buttonsMap: Map<Product, Boolean> = new Map<Product, Boolean>();

  public selectedCategory = null;
  public productsPerPage = 10;
  public selectedPage = 1;

  public categories: Category[] = [];

  public quantity: number = 1;

  public cart: Cart;


  constructor(public productService: ProductService,
              public router: Router,
              public cartService: CartService,
              public dialog: MatDialog,
              public wishListService: WishlistService,
              public eventEmitterService: EventEmitterService) {

    this.refreshData();

  }


  ngOnInit(): void {
    if (this.eventEmitterService.subsVar==undefined) {
      this.eventEmitterService.subsVar = this.eventEmitterService.
      invokeFirstComponentFunction.subscribe((name:string) => {
        this.refreshData();
        console.log("all-products-page-->");
      });

    }
  }

  updateProductStatus(newStatus: boolean, product: Product) {
    this.buttonsMap.set(product, newStatus);
  }

  getProducts(category: string = null): Product[] {
    return this.getAllProducts().filter(p => category == null || category == p.category);
  }

  public getAllProducts(): Product[] {
    return Array.from(this.buttonsMap.keys());
  }

  changeCategory(newCategory?: string) {
    this.selectedCategory = newCategory;
    console.log(this.selectedCategory);
  }

  changePage(newPage: number) {
    this.selectedPage = newPage;
  }

  get pageCount() {
    return Math.ceil(this.getProducts(this.selectedCategory).length / this.productsPerPage);
  }

  openAddToWishList(productId: number) {
    let wishId: number;

    const dialogRef = this.dialog.open(AddProductToWishListDialogComponent, {
      width: '450px',
      data: {title: wishId}
    });
    dialogRef.afterClosed().subscribe((x) => {
      wishId = x;
      console.log(x);
      if (x) {
        this.wishListService.addProductToWishList(productId, wishId).subscribe();
      }
    })
  }


  public refreshData() {


    console.log("inside refresh data");

    this.buttonsMap = new Map<Product, Boolean>();

    this.productService.findAllProducts().subscribe((data) => {

      data.forEach(currentProduct => {

        if (this.cartService.checkIfProductInCart(currentProduct.id)) {
          console.log("yes");
          this.buttonsMap.set(currentProduct, true);
        } else {
          this.buttonsMap.set(currentProduct, false);
          console.log("no");
        }
      })
    });
  }


}
