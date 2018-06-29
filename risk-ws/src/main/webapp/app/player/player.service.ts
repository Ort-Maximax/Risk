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

import { Player } from './player.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private static _baseUrl = 'api/players';

  public constructor(
    private _httpService: HttpClient
  ) { }

  public fetchPlayers(): Observable<Player[]> {
    return this._httpService.get<Player[]>(PlayerService._baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  public createPlayer(player: Player): Observable<Player> {
    return this._httpService.post<Player>(PlayerService._baseUrl, player, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public removePlayer(player: Player): Observable<Player> {
    return this._httpService.delete<Player>(`${PlayerService._baseUrl}/${player.id}`, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public fetchPlayer(id: number): Observable<Player> {
    return this._httpService.get<Player>(`${PlayerService._baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  public updatePlayer(player: Player): Observable<Player> {
    return this._httpService.put<Player>(`${PlayerService._baseUrl}/${player.id}`, player, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public searchPlayers(term: string): Observable<Player[]> {
    if (term) {
      term = term.trim();
    }
    if (!term) {
      return of([]);
    }
    return this._httpService.get<Player[]>(`${PlayerService._baseUrl}/?name=${term}`).pipe(
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
