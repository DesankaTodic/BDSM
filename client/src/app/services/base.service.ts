import { HttpHeaders } from "@angular/common/http";
import { Observable, of } from 'rxjs';

export class BaseService {
  /**
  * Handle Http operation that failed.
  * Let the app continue.
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  protected handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      return of(error as T);
    };
  }
}