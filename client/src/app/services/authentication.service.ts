import { User } from '../models/user';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class AuthenticationService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

  login(loginParam: any): Observable<{}> {
    return this.http.post(this.baseUrl + 'login',loginParam, {observe: 'response'})
      .pipe(
      tap((response: any) => console.log(`login`)),
      catchError(this.handleError('login failed', []))
      );
  }
 }