import { Component, OnInit } from '@angular/core';
import {saveAs as importedSaveAs} from 'file-saver';
import { BookService } from '../services/book.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  providers: [BookService]
})

export class SearchComponent implements OnInit {
  private fields = ['title', 'author', 'keywords', 'text', 'language'];
  private types = ['term', 'phrase', 'fuzzy'];

  private operations = ['and', 'or', 'not'];

  private simple_query = { field : "", value : ""};
  private simple_query_type = "";

  private advanced_query = { field1 : "",
                             value1 : "",
                             field2 : "",
                             value2 : "",
                             operation : ""
                            };

  private books = [];

  constructor(private bookService : BookService) { }

  ngOnInit() {
  }

  onSubmitSimple(form){
    console.log(this.simple_query);
    console.log(this.simple_query_type);

    if (this.simple_query_type === ""){
      alert("Error : choose search type.");
    }
    else if (this.simple_query.field == ""){
      alert("Error : choose search field.");
    }
    else {
      this.bookService.simpleSearch(this.simple_query_type, this.simple_query).subscribe(
        data => {
          this.books = data;
        },
        error => {
          alert("smt went wrong impossible");
        });
    }

    //form.reset();
  }

  onSubmitAdvanced() {
    if (this.advanced_query.operation === "")
      alert("Error choose operation.");
    else if (this.advanced_query.field1 == "" || this.advanced_query.field2 == "")
      alert("Error choose search field.");
    else {
      this.bookService.advancedSearch(this.advanced_query).subscribe(
        data => {
          this.books = data;
        },
        error => {
          alert("smt went wrong impossible");
        });
    }
  }


    download(id, title) {
    this.bookService.download(id).subscribe(blob => {
            importedSaveAs(blob, title);
        });
    }

}
