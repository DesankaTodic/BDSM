import { Category } from '../models/category';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {
  title = 'Add category';
  category: Category;
  
  constructor() { }

  ngOnInit() {
  }

}
