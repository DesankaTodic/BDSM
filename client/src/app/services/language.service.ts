import { Language } from '../models/language';
import { User } from '../models/user';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class LanguageService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

    create(language: Language): Observable<{}> {
      return this.http.post(this.baseUrl + 'createLan', language, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`create language`)),
        catchError(this.handleError('create language failed', []))
        );
    }   
  
    edit(language: Language): Observable<{}> {
        return this.http.put(this.baseUrl + 'editLan', language, {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`create language`)),
          catchError(this.handleError('create language failed', []))
          );
      }   
  
    getOne(id: number): Observable<{}> {
      return this.http.get(this.baseUrl + 'getOneLan/' + id, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`gatting language`)),
        catchError(this.handleError('getting language failed', []))
        );
    }   
  
    getAll(): Observable<{}> {
        return this.http.get(this.baseUrl + 'getAllLan', {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`gatting languages`)),
          catchError(this.handleError('getting languages failed', []))
          );
      }   
 }