import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {WishlistService} from "../../services/wishlist-service";
import {WishList} from "../../model/wishList";
import {CartService} from "../../services/cart-service";
import {Product} from "../../model/Product";
import {MatDialog} from "@angular/material/dialog";
import {MoveProductDialog} from "../wish-list-dialog/move-product-dialog.component";
import {AddNewWishListDialogComponent} from "../add-new-wish-list-dialog/add-new-wish-list-dialog.component";
import {EditTitleWishlistComponent} from "../edit-title-wishlist/edit-title-wishlist.component";
import {tryCatch} from "rxjs/internal-compatibility";


@Component({
  selector: 'app-wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.css']
})
export class WishListComponent implements OnInit {


  public wishLists: WishList[] = [];
  public selectedProductsMap: Map<Product, Boolean> = new Map<Product, Boolean>();

  public selectedProducts: Product[] = [];
  public foundWL = new WishList();

  public totalAmount: number;

  public wishListMap: Map<Product, Boolean> = new Map<Product, Boolean>();


  constructor(public wishListService: WishlistService, public cartService: CartService, public dialog: MatDialog) {
    this.wishListService.getAllWishListsByUser().subscribe((wishLists) => {
      this.wishLists = wishLists;

      wishLists.forEach(product => {
        if (this.cartService.checkIfProductInCart(product.id)) {
          this.wishListMap.set(product, true);
        } else {
          this.wishListMap.set(product, false);
        }
      })
    });
  }


  addProductToCard(productId: number) {
    this.cartService.addProductToCart(productId);
    console.log("addProductToCard");
  }

  ngOnInit(): void {
    // setTimeout(() => {
    //   this.selectedProducts = [];
    // });
  }

  updateProductStatus(newStatus: boolean, product: Product) {
    this.wishListMap.set(product, newStatus);
  }


  selectProduct(checked: boolean, productId: number, wishId: number) {

    let wishList = this.wishLists.find((wishList) => wishList.id == wishId);
    let selectedProd = wishList.productDto.find((product) => product.id == productId);

    if (checked) {
      this.selectedProductsMap.set(selectedProd, true);
    } else {
      this.selectedProductsMap.delete(selectedProd);
    }
  }

  selectAllProductInWishList(wishId: number) {
    let wishList = this.wishLists.find((wishList) => wishList.id == wishId);
    if (this.isAllProductsFromWishListSelected(wishList)) {
      wishList.productDto.forEach((product) => {
        this.selectedProductsMap.delete(product);
      })
    } else {
      wishList.productDto.forEach((product) => {
        this.selectedProductsMap.set(product, true);
      })
    }
  }

  isAllProductsFromWishListSelected(wishList: WishList): boolean {
    let isAllSelected: boolean = false;
    wishList.productDto.forEach((product => {
      if (this.selectedProductsMap.has(product)) {
        isAllSelected = true;
      } else {
        isAllSelected = false;
      }
    }));
    console.log(isAllSelected)
    return isAllSelected;
  }

  moveItems(fromWishId: number, toWishId: number) {
    let fromWish = this.wishLists.find((wishList) => wishList.id == fromWishId);
    let toWish = this.wishLists.find((wishList) => wishList.id == toWishId);

    this.selectedProductsMap.forEach(((value, key) => {
      fromWish.productDto.forEach((product)=>{
        if(key == product){
          toWish.productDto.push(key);
          this.selectedProductsMap.delete(key);
          fromWish.productDto.splice(fromWish.productDto.indexOf(key),1);
        }
      })
    }));
  }

  openMoveProductsDialog(fromWishId: number) {
   let toWishId;
    const dialogRef = this.dialog.open(MoveProductDialog, {
      width: '450px',
      data: {wishListIdForMove: toWishId}
    });

    dialogRef.afterClosed().subscribe((wishListId) => {
       toWishId = wishListId;
       this.moveItems(fromWishId,toWishId);
    });
  }

  openCreateWishListDialog() {
    let wishList: WishList = new WishList();
    let titleForNewWishList: string = '';
    const dialogRef = this.dialog.open(AddNewWishListDialogComponent, {
      width: '450px',
      data: {title: titleForNewWishList}
    });

    dialogRef.afterClosed().subscribe((title) => {
      titleForNewWishList = title;

      if (title != null) {
        wishList.title = title;
        wishList.productDto = [];
        this.wishListService.createWishList(wishList).subscribe((wishList)=>{
          this.wishLists.push(wishList);
        });
      }
    })
  }

  openEditTitleDialog(currentTitle: string): void {

    const dialogRef = this.dialog.open(EditTitleWishlistComponent, {
      width: '350px',
      data: {title: currentTitle}
    });


    let wishList = this.wishLists.find((wl) => wl.title == currentTitle);

    dialogRef.afterClosed().subscribe((newTitle) => {

      if (newTitle != null) {
        wishList.title = newTitle;

        this.wishListService.updateWishList(wishList).subscribe((x) => {
          wishList = x;
        })
      }
    });
  }


  deleteAllProducts(wishId: number): WishList {

    this.wishListService.deleteAllProductsFromWishList(wishId).subscribe((p) => {
      for (let wishL of this.wishLists) {
        if (wishL.id == wishId) {
          //wishL.productDto.splice(wishL.productDto.findIndex((p) => p.id == wishId), 1);
          // for (let prod of wishL.productDto) {
          //   wishL.productDto.splice(wishL.productDto.findIndex(isSameProduct), 1);
          // }
          wishL.productDto = [];
          wishL = this.foundWL;
          break;
        }
      }
    });
    console.log("inside" + this.wishLists);
    return this.foundWL;
  }

  deleteProductsFromWishList(wishId: number): WishList {

    //  if(this.selectedProducts == null){
    //    this.wishListService.deleteAllProductsFromWishList(wishId).subscribe((p) => {
    //      for (let wishL of this.wishLists) {
    //        if (wishL.id == wishId) {
    //          //wishL.productDto.splice(wishL.productDto.findIndex((p) => p.id == wishId), 1);
    //          // for (let prod of wishL.productDto) {
    //          //   wishL.productDto.splice(wishL.productDto.findIndex(isSameProduct), 1);
    //          // }
    //          wishL.productDto = [];
    //          wishL = this.foundWL;
    //          break;
    //        }
    //      }
    //    });
    //    console.log("inside" + this.wishLists);
    //    return this.foundWL;
    // }else {


    //
    // if (this.selectedProducts == null) {
    //   this.selectAllProducts(wishId);
    // } else {
    //   let wishList = this.wishLists.find((wl) => wl.id == wishId);
    //   console.log("Selected Products: ", this.selectedProducts);
    //
    //   this.wishListService.deleteProductsFromWishList(wishList.id, this.selectedProducts).subscribe();
    //   for (let i = 0; i < this.selectedProducts.length; i++) {
    //     wishList.productDto.splice(wishList.productDto.indexOf(this.selectedProducts[i]), 1);
    //   }
    //   this.selectedProducts = [];
    //
    //   console.log("inside" + this.wishLists);
    //   return wishList;
    // }
    return null;
  }

  deleteWishList(wishId: number) {
    this.wishListService.deleteWishList(wishId).subscribe((p) => {
      this.wishLists.splice(this.wishLists.findIndex((p) => p.id === wishId), 1);
    });
  }


  getAllWishLists(): WishList[] {
    this.wishListService.getAllWishLists().subscribe((data) => {
      this.wishLists = data;
    });
    return this.wishLists;
  }

  public getWishListById(id: number): WishList {
    return this.wishLists.find((x) => x.id === id);
  }


  //deleteWishList(wishId: number) {
  //     this.wishListService.deleteWishList(wishId).subscribe((p) => {
  //       this.wishLists.splice(this.wishLists.findIndex((p) => p.id === wishId), 1);
  //     });
  //   }

  updateWishList(wishList: WishList) {
    // this.wishListService.updateWishList(wishList).subscribe((p) =>{
    //   for(let wl of this.wishLists){
    //     wl.title == wishList.title;
    //     wl.productDto == null;
    //   }
    // })
    this.wishListService.updateWishList(wishList);
  }


  public getAmountOfCheckedProducts(wishId: number): number {
    this.totalAmount = 0;
    let wishList = this.wishLists.find((wl) => wl.id == wishId);

    // for(let i = 0; i < this.selectedProducts.length; i++){
    //   this.totalAmount =
    // }
    this.selectedProductsMap.forEach((value,selectedProd) => {
      wishList.productDto.forEach(prodDto => {

        if (selectedProd == prodDto) {
          this.totalAmount += prodDto.price;
        }

      })
    });
    return this.totalAmount;
  }


  public addSelectedProductsToCart(wishId: number) {

    let wishList = this.wishLists.find((wl) => wl.id == wishId);
    // wishList.productDto.forEach(prodDto=>{
    // this.cartService.addProductToCart(prodDto).subscribe();}

    // console.log("Selected Products: ", this.selectedProducts);
    this.selectedProducts.forEach(selectedProd => {
      console.log("Selected Products: ", selectedProd);
      wishList.productDto.forEach(prodDto => {

        if (selectedProd == prodDto) {
          this.cartService.addProductToCart(prodDto.id);
        }

      })
    });
    this.selectedProducts = [];
  }

}

function isSameProduct(someProduct, index, products) {

  return products[index].id == someProduct.id;
}
