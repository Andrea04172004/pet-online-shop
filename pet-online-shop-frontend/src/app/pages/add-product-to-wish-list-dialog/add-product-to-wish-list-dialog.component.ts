import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

import {WishlistService} from "../../services/wishlist-service";
import {WishList} from "../../model/wishList";

@Component({
  selector: 'app-add-product-to-wish-list-dialog',
  templateUrl: './add-product-to-wish-list-dialog.component.html',
  styleUrls: ['./add-product-to-wish-list-dialog.component.css']
})
export class AddProductToWishListDialogComponent implements OnInit {
  public wishList: WishList[] = [];

  constructor(
    public wishListService: WishlistService) {

    this.wishListService.getAllWishListsByUser().subscribe((data)=>{
      this.wishList = data;
    })
  }

  ngOnInit(): void {
  }

}
