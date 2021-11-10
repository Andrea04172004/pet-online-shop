import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";


export interface DialogData {
  title: string;
}
@Component({
  selector: 'app-add-new-wish-list-dialog',
  templateUrl: './add-new-wish-list-dialog.component.html',
  styleUrls: ['./add-new-wish-list-dialog.component.css']
})
export class AddNewWishListDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddNewWishListDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

}
