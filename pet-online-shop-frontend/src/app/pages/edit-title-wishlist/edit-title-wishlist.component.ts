import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

export interface DialogData {
  title: string;
}

@Component({
  selector: 'app-edit-title-wishlist',
  templateUrl: './edit-title-wishlist.component.html',
  styleUrls: ['./edit-title-wishlist.component.css']
})
export class EditTitleWishlistComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditTitleWishlistComponent>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
       }

}
