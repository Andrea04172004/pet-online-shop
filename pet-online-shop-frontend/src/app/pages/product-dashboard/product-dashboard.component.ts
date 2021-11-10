import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {Category} from '../../model/Category';
import {Product} from '../../model/Product';
import {CategoryService} from '../../services/category-service';
import {ProductService} from '../../services/product-service';
import {CartService} from "../../services/cart-service";
import {stringify} from "@angular/compiler/src/util";
import {ProductLabels} from "../../model/enums/product-label.enum";
import * as feather from 'feather-icons';
import {UserService} from "../../services/user-service";
import {UserRoles} from "../../model/enums/user-roles.enum";
import {JwtResponse} from "../../response/JwtResponse";

@Component({
  selector: 'app-product-dashboard',
  templateUrl: './product-dashboard.component.html',
  styleUrls: ['./product-dashboard.component.css'],
})
export class ProductDashboardComponent implements OnInit{

  public id: number;
  public name: string;
  public price: number;
  public expirationDate: string;
  public quantity: number;
  public category: string;
  public image: string;
  public productLabel: ProductLabels;


  public labels = ProductLabels;
  public keys = Object.keys;


  public productPerPage: number = 10;
  public selectedPage: number = 1;
  public selectedCategory: string = null;

  public searchField: string = null;

  public product = new Product();

  private products: Product[] = [];
  private categories: Category[] = [];

  private newProduct: Product = {};




  constructor(private productService: ProductService, private categoryService: CategoryService,
              private cartService: CartService, private userService: UserService,
              private route: ActivatedRoute, private router: Router) {
    // this.id = this.route.snapshot.paramMap.get('id');

    this.categoryService.findAllCategories().subscribe((data) => {
      this.categories = data;
    });

    this.productService.findAllProducts().subscribe((data) => {
      this.products = data;
    });


  }

  ngOnInit(): void {
  }

  public getAllCategories(category?: string): Category[] {
    return this.categories.filter(p => category == null || category != p.name);
  }

  public findProductById(id: number): Product {
    return this.product;
  }

  public updateProduct(): void {

    this.productService.updateProduct(new Product(this.product.id, this.product.name, this.product.price,
      this.product.image, this.product.expirationDate, this.product.quantity, this.product.category, this.product.productLabel)).subscribe();
    // this.router.navigate(['/product-dashboard']);
  }

  public changeData(event: any, property: string): void {
    if (property === 'name') {
      this.product.name = event.target.textContent;
    }
    if (property === 'price') {
      this.product.price = event.target.textContent;
    }
    if (property === 'expirationDate') {
      this.product.expirationDate = event.target.value;
    }
    if (property === 'quantity') {
      this.product.quantity = event.target.textContent;
    }
    if (property === 'category') {
      this.product.category = event.target.value;
    }
    if (property === 'productLabel') {
      this.product.productLabel = event.target.value;
    }
  }

  public getProductByFind(id: number): Product {
    return this.products.find((x) => x.id === id);
  }

  public updateList(id: number, property: string, event: any): void {
    this.product = this.getProductByFind(id);
    this.changeData(event, property);

    this.updateProduct();
  }

  public deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe((p) => {
      // tslint:disable-next-line:no-shadowed-variable triple-equals
      this.products.splice(this.products.findIndex((p) => p.id == id), 1);
    });
  }


//pagination
  getProducts(category: string = null, searchField: string = null): Product[] {
    return this.products.filter(p => category == null || category == p.category)
      .filter(p => searchField == null || searchField == '' ||
        p.name.includes(searchField) || p.productLabel.toLocaleLowerCase().includes(searchField) ||
        p.category.includes(searchField) || p.expirationDate.includes(searchField) ||
        p.quantity.toString().includes(searchField) || p.price.toString().includes(searchField));
  }

  public getAllProducts(): Product[] {
    const pageIndex = (this.selectedPage - 1) * this.productPerPage;
    return this.getProducts(this.selectedCategory, this.searchField)
      .slice(pageIndex, pageIndex + this.productPerPage);
  }

  changeCategory(newCategory?: string) {
    this.selectedCategory = newCategory;
  }

  changePage(newPage: number) {
    if(newPage <= this.pageCount && newPage >= 1) {
      this.selectedPage = newPage;
    }
  }

  changePageSize(newSize: number) {
    this.productPerPage = Number(newSize);
    this.changePage(1);
  }


  get pageCount() {
    return Math.ceil(this.getProducts(this.selectedCategory).length / this.productPerPage);
  }

  //pagination

  //add new product
  // public addProduct() {
  //   this.addProductForm = false;
  //   console.log(this.productLabel, this.name, this.category, this.price);
  //
  //   this.productService.addProduct(new Product(1, this.name, this.price, this.image, this.expirationDate,
  //     this.quantity, this.category, this.productLabel)).subscribe();
  //
  //   // if (this.name != null && this.price != null) {
  //   //   this.products.push(new Product(1, this.name, this.price, this.image, this.expirationDate,
  //   //     this.quantity, this.category, this.productLabel));
  //   // }
  // }

  //add new product
}
