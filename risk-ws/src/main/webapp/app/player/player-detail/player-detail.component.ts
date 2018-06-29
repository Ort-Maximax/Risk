import { Location } from '@angular/common';
import {
  Component,
  OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  Observable,
  throwError
} from 'rxjs';
import {
  catchError,
  switchMap
} from 'rxjs/operators';

import { MessageService } from '../../message';
import { PlayerService } from '../player.service';

import { Player } from '../player.model';

@Component({
  selector: 'app-player-detail',
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css']
})
export class PlayerDetailComponent implements OnInit {

  private _player$: Observable<Player>;

  public constructor(
    private _activatedRoute: ActivatedRoute,
    private _location: Location,
    private _messageService: MessageService,
    private _playerService: PlayerService
  ) { }

  public ngOnInit(): void {
    this._player$ = this._activatedRoute.paramMap.pipe(
      switchMap(paramMap => this._playerService.fetchPlayer(+paramMap.get('id'))),
      catchError(error => {
        this._messageService.add(error);
        return throwError(error);
      })
    );
  }

  public get player$(): Observable<Player> {
    return this._player$;
  }

  public back(): void {
    this._location.back();
  }

  public save(player: Player): void {
    this._playerService.updatePlayer(player).subscribe();
    this._location.back();
  }

}
