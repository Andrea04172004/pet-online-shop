import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {url} from "../../environments/environment";
import {WishList} from "../model/wishList";
import {Product} from "../model/Product";
import {WishListComponent} from "../pages/wish-list/wish-list.component";

@Injectable({providedIn: 'root'})
export class WishlistService {

  constructor(private http: HttpClient) {

  }


  public createWishList(wishList: WishList): Observable<WishList>{
    const saveWishListUrl = `${url}/wish-list/createWishList`;
    return this.http.post<WishList>(saveWishListUrl, wishList);
  }

  public updateWishList(wishList: WishList): Observable<WishList>{
    const updateWishListUrl = `${url}/wish-list/updateWishList`;
    return this.http.put<WishList>(updateWishListUrl, wishList);
  }

  public deleteWishList(id: number): Observable<WishList>{
    const deleteWishListUrl = `${url}/wish-list/deleteWishList/${id}`;
    return this.http.delete(deleteWishListUrl);
  }

  public findWishListById (id: number): Observable<WishList>{
    const findWishListByIdUrl = `${url}/wish-list/get-by${id}`;
    return this.http.get<WishList>(findWishListByIdUrl);
  }

  public getAllWishLists(): Observable<WishList[]>{
    const getAllWishListsUrl = `${url}/wish-list/getAllWishLists`;
    return this.http.get<WishList[]>(getAllWishListsUrl);
  }

  public getAllWishListsByUser(): Observable<WishList[]> {
    const getAllWishListsByUserUrl = `${url}/wish-list/getAllWishListsByUser`;
    return this.http.get<WishList[]>(getAllWishListsByUserUrl);
  }

  public getWishListByTitle(title: string): Observable<WishList>{
    const getWishListsByUserIdUrl = `${url}/wish-list/getWishListByTitle${title}`;
    return this.http.get<WishList>(getWishListsByUserIdUrl);
  }

  public getProductsByWishId(id: number): Observable<Product[]>{
    const getProductsByWishIdUrl = `${url}/wish-list/getProductsByWishId${id}`;
    return this.http.get<Product[]>(getProductsByWishIdUrl);
  }

  public addProductToWishList(productId: number, wishId: number): Observable<WishList>{
    const addProductToWishListUrl = `${url}/wish-list/addToWishList/${productId}/${wishId}`;
    return this.http.get<WishList>(addProductToWishListUrl);
  }
  //
  //    @PutMapping ("/addToWishList/{productId}/{wishId}")
  //    public ResponseEntity<WishListDto> addProductToWishList(@PathVariable("productId") int productId,
  //                                            @PathVariable ("wishId") int wishListId) {
  //       return ResponseEntity.ok(shopService.addProductToWishList(productId,wishListId));
  //     }


  // public deleteAllProductsFromWishList(wishId: number): Observable<WishList>{
  //   const deleteAllProductsFromWishListUrl = `${url}/wish-list${wishId}/clear`;
  //   // const deleteAllProductsFromWishListUrl = `${url}/wish-list/deleteAllProductsFromWishList${wishId}`;
  //   console.log(deleteAllProductsFromWishListUrl);
  //   return this.http.get<WishList>(deleteAllProductsFromWishListUrl);
  // }

  public deleteAllProductsFromWishList(wishId: number): Observable<WishList>{
    const deleteAllProductsFromWishListUrl = `${url}/wish-list/deleteAllProductsFromWishList/${wishId}`;
    // const deleteAllProductsFromWishListUrl = `${url}/wish-list/deleteAllProductsFromWishList${wishId}`;
    console.log(deleteAllProductsFromWishListUrl);
    return this.http.get<WishList>(deleteAllProductsFromWishListUrl);
  }

  public deleteProductFromWishList(wishId: number, productId: number): Observable<WishList>{
    const deleteProductFromWishList = `${url}/wish-list/deleteProduct/${productId}/formWish/${wishId}`;
    return this.http.delete(deleteProductFromWishList);
  }

  public moveProductToWish(fromWishId: number,toWishId: number, products: Product[]): Observable<WishList>{
    const moveToAnotherWishList = `${url}/wish-list/moveProduct/${fromWishId}/${toWishId}`;
    console.log("Selected prod->", products)
    return this.http.put<WishList>(moveToAnotherWishList, products);
  }

  public deleteProductsFromWishList(wishId: number, products: Product[]): Observable<WishList>{
    const deleteProductsFromWLUrl = `${url}/wish-list/deleteProducts/${wishId}`;
    console.log("products: " + products);
    return this.http.put<WishList>(deleteProductsFromWLUrl, products);
  }
  //@PutMapping("/deleteProducts/{wishId}")
  //     public ResponseEntity<WishListDto> deleteProductListFromWishList(@PathVariable("wishId") int wishId, @RequestBody List<ProductDto> productDtos){
  //         return ResponseEntity.ok(shopService.deleteProductListFromWishList(wishId, productDtos));
  //     }
}
