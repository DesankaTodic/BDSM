import { Book } from '../models/book';
import { Category } from '../models/category';
import { Language } from '../models/language';
import { BookService } from '../services/book.service';
import { CategoryService } from '../services/category.service';
import { LanguageService } from '../services/language.service';
import { LuceneService } from '../services/lucene.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
  providers: [BookService, LanguageService, CategoryService, LuceneService]
})
export class AddBookComponent implements OnInit {
  pageTitle = 'Add book';
  book: Book = new Book();
  categories: Category[];
  languages: Language[];
  bookId: number;
  file: File;
  isEdit: boolean = false;
  readonlyMetadata: boolean = true;

  constructor(private route: ActivatedRoute, private bookService: BookService, private luceneService: LuceneService, private languageService: LanguageService,
    private categoryService: CategoryService, private router: Router) {
    this.route.params.subscribe(params => {
      this.bookId = +params['id'];
    });
    this.book.id = -1;
  }

  ngOnInit() {
    this.initBook();
    this.initCategories();
    this.initLanguages();
  }

  initBook() {
    if (this.bookId) {
      this.isEdit = true;
      this.readonlyMetadata = false;
      this.bookService.getOne(this.bookId).subscribe((data: any) => {
        if (data.status == 200) {
          this.book = data.body;
          this.pageTitle = 'Edit book';
          //alert("Get category done!");
        } else {
          alert("Failed. Try again.")
        }
      }, () => console.log("Get book completed"));
    }
  }

  initCategories() {
    this.categoryService.getAll().subscribe((data: any) => {
      if (data.status == 200) {
        this.categories = data.body;
        if(!this.isEdit) {
          this.book.categoryId = data.body[0].id;
        }
        //alert("Get categories done!");
      } else {
        alert("Failed. Try again.")
      }
    }, () => console.log("Get categories completed"));
  }

  initLanguages() {
    this.languageService.getAll().subscribe((data: any) => {
      if (data.status == 200) {
        this.languages = data.body;
        if(!this.isEdit) {
            this.book.languageId = data.body[0].id;
        }
        //alert("Get categories done!");
      } else {
        alert("Failed. Try again.")
      }
    }, () => console.log("Get languagaes completed"));
  }

  fetchMetadata(fileList: FileList) {
    if (fileList.length > 0) {
      this.file = fileList.item(0);
      let formData: FormData = new FormData();
      formData.append('file', this.file);
      this.bookService.fetchMetadata(formData).subscribe((data: any) => {
        if (data.status == 200) {
          this.book.title = data.body.Title;
          this.book.author = data.body.Author;
          this.book.keywords = data.body.Keywords;
          this.readonlyMetadata = false;
          //alert("Get categories done!");
        } else {
          alert("Failed. Try again.")
        }
      }, () => console.log("Get categories completed"));
    }
  }

  save() {
    if (!this.isEdit) {
      let payload = new FormData();

      payload.append('files', this.file);
      payload.append('title', this.book.title);
      payload.append('author', this.book.author);
      payload.append('keywords', this.book.keywords);
      payload.append('id', this.book.id.toString());
      payload.append('language', this.book.languageId.toString());
      payload.append('category', this.book.categoryId.toString());

      this.luceneService.save(payload).subscribe((data: any) => {
        if (data.status == 200) {
          alert('Successfully uploaded the book.');
          this.router.navigateByUrl('/books');
        } else {
          alert("Failed. Try again.")
        }
      }, () => console.log("Save book completed"));
    } else {
        this.bookService.updateMetadata(this.book).subscribe((data: any) => {
        if (data.status == 200) {
          alert('Successfully updated the book.');
          this.router.navigateByUrl('/books');
        } else {
          alert("Failed. Try again.")
        }
      }, () => console.log("Edit book completed"));
    }
  }

}
