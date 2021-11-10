import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Product} from '../../model/Product';
import {ProductService} from '../../services/product-service';
import {Category} from "../../model/Category";
import {CategoryService} from "../../services/category-service";
import {ProductLabels} from "../../model/enums/product-label.enum";
import {throwUnknownPortalTypeError} from "@angular/cdk/portal/portal-errors";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  // tslint:disable-next-line:object-literal-sort-keys
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent {
  public id: string;
  public name: string;
  public price: number;
  public expirationDate: string;
  public quantity: number;
  public category: string;
  public inputImage: string;
  public image: string[] = [];
  public product: Product;
  @Input() productLabel: string;

  public visibility: boolean = true;

  public labels = ProductLabels;
  public keys = Object.keys;

  private categories: Category[] = [];
  constructor(private productService: ProductService, private router: Router,public categoryService: CategoryService) {
    this.categoryService.findAllCategories().subscribe((data) => {
      this.categories = data;
    });
  }


  public convertStringToNumber(input: string) {
    const numeric = Number(input);
    return numeric;
  }

  // tslint:disable-next-line:typedef
  public addProduct() {
    this.visibility = false;
    console.log(this.productLabel, this.name, this.category, this.price, this.image);
    this.image.push(this.inputImage);
    this.productService.addProduct(new Product(1, this.name, this.price, this.image, this.expirationDate,
      this.quantity, this.category, this.productLabel)).subscribe();
  }

  public getAllCategories(category?: string): Category[] {
    return this.categories.filter(p => category == null || category != p.name);
  }
}
