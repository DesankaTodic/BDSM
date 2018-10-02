import { Category } from '../models/category';
import { CategoryService } from '../services/category.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'], 
  providers: [CategoryService]
})
export class CategoriesComponent implements OnInit {
  categories: Category[];
  constructor(private router: Router, private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getAll().subscribe((data: any) => {
        if (data.status == 200) {
            this.categories = data.body;
            //alert("Get categories done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("Get categories completed"));
     }

  edit(id: number) {
    this.router.navigate(['/categories/add', id]);
  }
}
