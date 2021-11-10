import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Category} from '../../model/Category';
import {Product} from '../../model/Product';
import {CategoryService} from '../../services/category-service';
import {ProductService} from '../../services/product-service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css'],
})
export class UpdateProductComponent {

  public id: number;
  public name: string;
  public price: number;
  public expirationDate: string;
  public quantity: number;
  public category: string;
  public image: string;
  public productLabel: string;

  public editField: string;

  public product = new Product();

  private products: Product[] = [];

  private categories: Category[] = [];

  constructor(private productService: ProductService, private categoryService: CategoryService,
              private route: ActivatedRoute, private router: Router) {
    // this.id = this.route.snapshot.paramMap.get('id');

    this.categoryService.findAllCategories().subscribe((data) => {
      this.categories = data;
    });

    this.productService.findAllProducts().subscribe((data) => {
      this.products = data;
    });
  }

  public ngOnInit(): void {

  }

  public getAllCategories(): Category[] {
    return this.categories;
  }

  public getAllProducts(): Product[] {
    return this.products;
  }

  public findProductById(id: number): Product {
    return this.product;
  }

  public update(): void {

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
      this.product.expirationDate = event.target.textContent;
    }
    if (property === 'quantity') {
      this.product.quantity = event.target.textContent;
    }
    if (property === 'category') {
      this.product.category = event.target.textContent;
    }
    if (property === 'productLabel') {
      this.product.productLabel = event.target.textContent;
    }
  }

  public getProductByFind(id: number): Product {
    return this.products.find((x) => x.id === id);
  }

  public updateList(id: number, property: string, event: any): void {
    console.log(id, property, event.target.textContent);
    this.product = this.getProductByFind(id);
    this.changeData(event, property);

    this.update();
  }

}
