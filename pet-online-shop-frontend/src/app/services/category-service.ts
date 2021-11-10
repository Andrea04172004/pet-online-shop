/* tslint:disable:ordered-imports */
import {Injectable} from '@angular/core';
import {url} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../model/Category';
import {Product} from "../model/Product";

@Injectable({providedIn: 'root'})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  public addCategory(category: Category): Observable<Category> {

    const addCategoryUrl = `${url}/category/add`;

    return this.http.post<Category>(addCategoryUrl, category);

  }

  public findAllCategories(): Observable<Category[]> {
    const finaAllCategoriesUrl = `${url}/category/get`;
    return this.http.get<Category[]>(finaAllCategoriesUrl);
  }



}
