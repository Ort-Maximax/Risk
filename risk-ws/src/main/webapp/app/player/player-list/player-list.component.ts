import {
  Component,
  OnInit
} from '@angular/core';

import { Player } from '../player.model';
import { PlayerService } from '../player.service';

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {

  private _players: Player[];

  public constructor(
    private _playerService: PlayerService
  ) { }

  public ngOnInit() {
    this.fetchPlayers();
  }

  public get players(): Player[] {
    return this._players;
  }

  private fetchPlayers(): void {
    this._playerService.fetchPlayers()
      .subscribe(players => this._players = players)
    ;
  }

  public add(name: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this._playerService.createPlayer({name: name} as Player)
      .subscribe(player => {
        this._players.push(player);
      })
    ;
  }

  public remove(player: Player): void {
    this._players = this._players.filter(item => item !== player);
    this._playerService.removePlayer(player).subscribe();
  }

}
