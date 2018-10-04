import { Book } from '../models/book';
import { Category } from '../models/category';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class LuceneService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

    save(bookData: FormData): Observable<{}> {
      return this.http.post(this.baseUrl + '/index/add', bookData, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`create book`)),
        catchError(this.handleError('create book failed', []))
        );
    }   
 }