import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {url} from '../../environments/environment';
import {Product} from '../model/Product';

@Injectable({providedIn: 'root'})
export class ProductService {

  constructor(private http: HttpClient) {
    // const id: Observable<string> = route.params.pipe(map(p => p.id));
  }

  public findProductByName(name: string): Observable<Product[]> {
    const findProductUrl = `${url}/product/find-by-name/${name}`;

    return this.http.get<Product[]>(findProductUrl);
  }

  public findProductById(id: number): Observable<Product> {
    const findProductByIdUrl = `${url}/product/find-by/${id}`;
    return this.http.get(findProductByIdUrl);
  }

  public findAllProducts(): Observable<Product[]> {
    const findAllProductsUrl = `${url}/product/show-all`;
    return this.http.get<Product[]>(findAllProductsUrl);
  }

  public deleteProduct(id: number): Observable<Product> {
    const deleteProductUrl = `${url}/product/delete/${id}`;
    return this.http.get(deleteProductUrl);
  }

  public updateProduct(product: Product): Observable<Product> {

    const updateProductUrl = `${url}/product/update`;

    return this.http.post<Product>(updateProductUrl, product);

  }

  public addProduct(product: Product): Observable<Product> {

    const addProductUrl = `${url}/product/add`;

    return this.http.post<Product>(addProductUrl, product);
  }

}
