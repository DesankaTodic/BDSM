import { Book } from '../models/book';
import { Category } from '../models/category';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';
import { Http, Response } from '@angular/http';
import { RequestOptions, ResponseContentType } from '@angular/http';

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
  
  fetchMetadata(file: FormData): Observable<{}> {
      return this.http.post(this.baseUrl + 'metadata', file, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`get metadata book`)),
        catchError(this.handleError('get metadata book failed', []))
        );
    }  

    // https://stackoverflow.com/questions/35138424/how-do-i-download-a-file-with-angular2
    download(id: number) {
      return this.http.get(this.baseUrl + 'download/' + id, {responseType: 'blob' })
    }

    simpleSearch(type, query) {
      return this.http.post("http://localhost:8080/search/" + type, query) as Observable<any>;
    }

    advancedSearch(advancedQuery) {
      return this.http.post("http://localhost:8080/search/boolean", advancedQuery) as Observable<any>;
    }

    updateMetadata(book: Book): Observable<{}>{
      return this.http.put("http://localhost:8080/update/metadata", book, {observe: 'response'}) as Observable<any>;

    }
 }