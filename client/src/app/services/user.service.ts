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
    return this.http.post<any>(this.baseUrl + 'registration',user, {observe: 'response'})
      .pipe(
      tap((response: any) => console.log(`registration`)),
      catchError(this.handleError('registration failed', []))
      );
  }
  
    edit(user:User): Observable<{}> {
        return this.http.put(this.baseUrl + 'editUser', user, {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`edit user`)),
          catchError(this.handleError('edit user failed', []))
          );
      }   
  
    getOne(id: number): Observable<{}> {
      return this.http.get(this.baseUrl + 'getOneUser/' + id, {observe: 'response'})
        .pipe(
        tap((response: any) => console.log(`getting user`)),
        catchError(this.handleError('getting user failed', []))
        );
    }   
  
    getAll(): Observable<{}> {
        return this.http.get(this.baseUrl + 'getAllUser', {observe: 'response'})
          .pipe(
          tap((response: any) => console.log(`getting users`)),
          catchError(this.handleError('getting users failed', []))
          );
      }   
 }