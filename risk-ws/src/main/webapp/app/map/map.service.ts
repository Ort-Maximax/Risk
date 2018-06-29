import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  Observable,
  of,
  throwError
} from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Map } from './map.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private static _baseUrl = 'api/maps';

  public constructor(
    private _httpService: HttpClient
  ) { }

  public fetchMaps(): Observable<Map[]> {
    return this._httpService.get<Map[]>(MapService._baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  public createMap(map: Map): Observable<Map> {
    return this._httpService.post<Map>(MapService._baseUrl, map, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public removeMap(map: Map): Observable<Map> {
    return this._httpService.delete<Map>(`${MapService._baseUrl}/${map.id}`, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public fetchMap(id: number): Observable<Map> {
    return this._httpService.get<Map>(`${MapService._baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public updateMap(map: Map): Observable<Map> {
    return this._httpService.put<Map>(`${MapService._baseUrl}/${map.id}`, map, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public searchMaps(term: string): Observable<Map[]> {
    if (term) {
      term = term.trim();
    }
    if (!term) {
      return of([]);
    }
    return this._httpService.get<Map[]>(`${MapService._baseUrl}/?name=${term}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }

}
