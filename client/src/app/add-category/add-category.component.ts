import { Category } from '../models/category';
import { CategoryService } from '../services/category.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css'], 
  providers: [CategoryService]
})
export class AddCategoryComponent implements OnInit {
  title = 'Add category';
  category: Category = new Category();
  catId: number;
  constructor(private route: ActivatedRoute,private categoryService: CategoryService, private router: Router) { 
  this.route.params.subscribe(params => {
      this.catId = +params['id'];
    });
  }

  ngOnInit() {
    if(this.catId) {
      this.categoryService.getOne(this.catId).subscribe((data: any) => {
        if (data.status == 200) {
            this.category = data.body;
            this.title = 'Edit category';
            //alert("Get category done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("Get category completed"));
     }
    }

   save() {
     if(!this.category.id) {
       this.categoryService.create(this.category).subscribe((data: any) => {
        if (data.status == 201) {
            this.router.navigateByUrl('/categories');
            alert("Create category done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("create category completed"));
     } else {
        this.categoryService.edit(this.category).subscribe((data: any) => {
        if (data.status == 200) {
            this.router.navigateByUrl('/categories');
            alert("Edit category done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("Edit category completed"));
     }
   } 
}

