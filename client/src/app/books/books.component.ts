import { Book } from '../models/book';
import { Category } from '../models/category';
import { BookService } from '../services/book.service';
import { CategoryService } from '../services/category.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { saveAs as importedSaveAs } from 'file-saver';


@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css'],
  providers: [BookService, CategoryService]
})
export class BooksComponent implements OnInit {
  categoryId = -1;
  books: Book[];
  categories: Category[];

  role: string;
  subscriberCategory: number | null;

  constructor(private router: Router, private bookService: BookService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.initBooks();
    this.initCategories();
    this.role = localStorage.getItem('role');
    this.subscriberCategory = +localStorage.getItem('category');
  }

  checkIfLoggedIn() {
    return localStorage.getItem('user') != null;
  }

  initBooks() {
    this.bookService.getAllFromCategory(this.categoryId).subscribe((data: any) => {
      if (data.status === 200) {
        this.books = data.body;
        //alert("Get categories done!");
      } else {
        alert("smt went wrong impossible")
      }
    }, () => console.log("Get categories completed"));
  }

  initCategories() {
    this.categoryService.getAll().subscribe((data: any) => {
      if (data.status === 200) {
        this.categories = data.body;
        //alert("Get categories done!");
      } else {
        alert('smt went wrong impossible')
      }
    }, () => console.log('Get categories completed'));
  }
  edit(id: number) {
    this.router.navigate(['/books/add', id]);
  }

  download(id, title) {
    this.bookService.download(id).subscribe(blob => {
      importedSaveAs(blob, title);
    });
  }
}