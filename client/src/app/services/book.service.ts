import { Book } from '../models/book';
import { Category } from '../models/category';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class BookService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

    create(book: Book): Observable<{}> {
      return this.http.post(this.baseUrl + 'createBook', book, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`create book`)),
        catchError(this.handleError('create book failed', []))
        );
    }   
  
    edit(book: Book): Observable<{}> {
        return this.http.put(this.baseUrl + 'editBook', book, {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`edit book`)),
          catchError(this.handleError('edit book failed', []))
          );
      }   
  
    getOne(id: number): Observable<{}> {
      return this.http.get(this.baseUrl + 'getOneBook/' + id, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`getting book`)),
        catchError(this.handleError('getting book failed', []))
        );
    } 
    
    getAllFromCategory(id: number): Observable<{}> {
      return this.http.get(this.baseUrl + 'getBooksForCat/' + id, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`getting books for cat`)),
        catchError(this.handleError('getting book for cat failed', []))
        );
    } 
  
  fetchMetadata(file: File): Observable<{}> {
      return this.http.post(this.baseUrl + 'metadata', file, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`get metadata book`)),
        catchError(this.handleError('get metadata book failed', []))
        );
    }   
 }