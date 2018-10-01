import { Book } from '../models/book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {
  title = 'Add book';
  book: Book;
  constructor() { }

  ngOnInit() {
  }

}
