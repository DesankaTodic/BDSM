import {Book} from '../models/book';
import {Category} from '../models/category';
import {Language} from '../models/language';
import {BookService} from '../services/book.service';
import {CategoryService} from '../services/category.service';
import {LanguageService} from '../services/language.service';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
  providers: [BookService, LanguageService, CategoryService]
})
export class AddBookComponent implements OnInit {
  title = 'Add book';
  book: Book = new Book();
  categories: Category[];
  languages: Language[];
  bookId: number;
  file: File;
  constructor(private route: ActivatedRoute, private bookService: BookService, private languageService: LanguageService, 
    private categoryService: CategoryService, private router: Router) {
    this.route.params.subscribe(params => {
      this.bookId = +params['id'];
    });
    this.book.categoryId = null;
    this.book.languageId = null;
  }

  ngOnInit() {
    this.initBook();
    this.initCategories();
    this.initLanguages();
  }

  initBook() {
    if (this.bookId) {
      this.bookService.getOne(this.bookId).subscribe((data: any) => {
        if (data.status == 200) {
          this.book = data.body;
          this.title = 'Edit book';
          //alert("Get category done!");
        } else {
          alert("smt went wrong impossible")
        }
      }, () => console.log("Get book completed"));
    }
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

  initLanguages() {
    this.languageService.getAll().subscribe((data: any) => {
      if (data.status == 200) {
        this.languages = data.body;
        //alert("Get categories done!");
      } else {
        alert("smt went wrong impossible")
      }
    }, () => console.log("Get languagaes completed"));
  }
  
  fetchMetadata(fileList: FileList) {
    if (fileList.length > 0) {
      this.file = fileList.item(0);
      this.bookService.fetchMetadata(this.file).subscribe((data: any) => {
      if (data.status == 200) {
        this.book.title = data.body.Title;
        this.book.author = data.body.Author;
        this.book.keywords = data.body.Keywords;
        //alert("Get categories done!");
      } else {
        alert("smt went wrong impossible")
      }
    }, () => console.log("Get categories completed"));
    }
  }

}
