import {Component, OnInit} from '@angular/core';
import {Category} from "../../model/Category";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category-service";
import {error} from "@angular/compiler/src/util";

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  category: Category;

  constructor(private categoryService: CategoryService, private router: Router) {
    this.category = new Category();
  }

  ngOnInit(): void {
  }

  addCategory() {

    this.categoryService.addCategory(this.category).subscribe(p => {
      this.router.navigate(['/add-category'])
    }, error2 => {
      console.log(error2);
    })

  }

}
