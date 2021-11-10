import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../services/user-service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {EditUserComponent} from "../edit-user/edit-user.component";
import {NotificationServiceService} from "../../services/notification-service.service";
import {DialogService} from "../../services/dialog-service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: MatTableDataSource<any>;
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'role', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;


  constructor(private userService: UserService,
              private router: Router,
              private dialog: MatDialog,
              private notificationService: NotificationServiceService,
              private dialogService: DialogService) {
  };


  ngOnInit() {
    this.userService.findAllUsers().subscribe(
      users => {
        this.users = new MatTableDataSource(users);
        this.users.sort = this.sort;
        this.users.paginator = this.paginator;
      });
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.users.filter = this.searchKey.trim().toLowerCase();
  }

  onDelete(id: any) {
    this.dialogService.openConfirmDialog('\'Are you sure to delete this record ?\'')
    .afterClosed().subscribe(res => {
      if (res) {
        this.userService.deleteUser(id).subscribe();
        this.notificationService.warn('! Deleted successfully');
      }
    });
  }

  onEdit(id: any) {

    let userId: number;
    userId = id;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "50%";
    dialogConfig.data = {
      userId
    };
    this.dialog.open(EditUserComponent, dialogConfig);
  }


}
