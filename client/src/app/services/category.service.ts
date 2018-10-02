import { Category } from '../models/category';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class CategoryService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

    create(category: Category): Observable<{}> {
      return this.http.post(this.baseUrl + 'createCat', category, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`create category`)),
        catchError(this.handleError('create category failed', []))
        );
    }   
  
    edit(category: Category): Observable<{}> {
        return this.http.put(this.baseUrl + 'editCat', category, {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`edit category`)),
          catchError(this.handleError('edit category failed', []))
          );
      }   
  
    getOne(id: number): Observable<{}> {
      return this.http.get(this.baseUrl + 'getOneCat/' + id, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`getting category`)),
        catchError(this.handleError('getting category failed', []))
        );
    }   
  
    getAll(): Observable<{}> {
        return this.http.get(this.baseUrl + 'getAllCat', {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`getting categories`)),
          catchError(this.handleError('getting categories failed', []))
          );
      }   
 }