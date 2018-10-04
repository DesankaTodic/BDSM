import { Book } from '../models/book';
import { Category } from '../models/category';
import { BookService } from '../services/book.service';
import { CategoryService } from '../services/category.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
  constructor(private router: Router, private bookService: BookService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.initBooks();
    this.initCategories();
  }
  initBooks() {
    this.bookService.getAllFromCategory(this.categoryId).subscribe((data: any) => {
      if (data.status == 200) {
        this.books = data.body;
        //alert("Get categories done!");
      } else {
        alert("smt went wrong impossible")
      }
    }, () => console.log("Get categories completed"));
  }

  initCategories() {
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

  download(id: number, title: string) {
    this.bookService.download(id).subscribe((data: any) => {
      if (data.status == 200) {
        
        alert("Download started!");
         let name = title;

        let fileBlob = data.blob();
        var a = document.createElement("a");
        document.body.appendChild(a);
        var file = new Blob([fileBlob], {type: 'application/pdf'});
        var fileURL = window.URL.createObjectURL(file);
        a.href = fileURL;
        a.download = name.toLowerCase() + ".pdf";
        a.click();

        return file;
      } else {
        alert("smt went wrong impossible")
      }
    }, () => console.log("Get download completed"));
  }
}
