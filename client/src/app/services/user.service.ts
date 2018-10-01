import { User } from '../models/user';
import { BaseService } from './base.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class UserService extends BaseService{

  constructor(private http: HttpClient)
  {
    super();
  }
  
  private baseUrl = 'http://localhost:8080/';

  registrate(user: User): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'registration',user)
      .pipe(
      tap((response: any) => console.log(`registration`)),
      catchError(this.handleError('registration failed', []))
      );
  }
 }