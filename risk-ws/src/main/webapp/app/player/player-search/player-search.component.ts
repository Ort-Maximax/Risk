import {
  Component,
  OnInit
} from '@angular/core';
import {
  Observable,
  Subject
} from 'rxjs';
import {
  debounceTime,
  distinctUntilChanged,
  switchMap
 } from 'rxjs/operators';

 import { PlayerService } from '../player.service';

 import { Player } from '../player.model';

@Component({
  selector: 'app-player-search',
  templateUrl: './player-search.component.html',
  styleUrls: [ './player-search.component.css' ]
})
export class PlayerSearchComponent implements OnInit {
  private _players$: Observable<Player[]>;
  private _subject = new Subject<string>();

  public constructor(
    private _playerService: PlayerService
  ) {}

  public ngOnInit(): void {
    this._players$ = this._subject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this._playerService.searchPlayers(term))
    );
  }

  public get players$() {
    return this._players$;
  }

  public next(term: string): void {
    this._subject.next(term);
  }

}
