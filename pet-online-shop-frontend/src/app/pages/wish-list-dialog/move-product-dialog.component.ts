import {Component, Input, Output, EventEmitter, OnInit, Inject} from "@angular/core";
import {WishlistService} from "../../services/wishlist-service";
import {WishList} from "../../model/wishList";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

export interface DialogData {
  wishListIdForMove: number;
}

@Component({
  selector: 'wish-dialog',
  templateUrl: './move-product-dialog.component.html',
})
export class MoveProductDialog implements OnInit {
  wishList: WishList[] = [];

  constructor(
    public dialogRef: MatDialogRef<MoveProductDialog>, public wishListService: WishlistService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  onSelectWishList(wishListId: number) {
    this.data.wishListIdForMove = wishListId;
    this.dialogRef.close(this.data.wishListIdForMove);
  }

  ngOnInit(): void {
    this.wishListService.getAllWishListsByUser().subscribe((wishLists) => {
      this.wishList = wishLists;
    })
  }

}

