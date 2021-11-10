import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Product} from '../../model/Product';
import {ProductService} from '../../services/product-service';

@Component({
  selector: 'app-find-product',
  templateUrl: './find-product.component.html',
  styleUrls: ['./find-product.component.css'],
})
export class FindProductComponent  {
  public searchName: string;
  public searchList: Product[] = [];

  constructor(public productService: ProductService, public router: Router) {

  }

  public getProductByName(): Product[] {
    console.log(this.searchName);
    if (this.searchName === '') {
      this.cleanSearch();
      return this.searchList;
    }
    this.productService.findProductByName(this.searchName).subscribe((data) => {
      this.searchList = data;
    });
    return this.searchList;
  }

  public getSearchList(): Product[] {
    return this.searchList;
  }

  public cleanSearch(): void {
    this.searchList = [];
  }

  public navigateToUpdateProduct(id: number): void {
    this.router.navigate(['product/update/', id]);
  }

  public navigateToAddProduct(): void {
    this.router.navigate(['add-product']);
  }

  public deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe((p) => {
      // tslint:disable-next-line:no-shadowed-variable triple-equals
      this.searchList.splice(this.searchList.findIndex((p) => p.id == id), 1);
    });
  }
}
