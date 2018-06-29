import {
  Component,
  OnInit
} from '@angular/core';

import { Map } from '../map.model';
import { MapService } from '../map.service';

@Component({
  selector: 'app-map-list',
  templateUrl: './map-list.component.html',
  styleUrls: ['./map-list.component.css']
})
export class MapListComponent implements OnInit {

  private _maps: Map[];

  public constructor(
    private _mapService: MapService
  ) { }

  public ngOnInit() {
    this.fetchMaps();
  }

  public get maps(): Map[] {
    return this._maps;
  }

  private fetchMaps(): void {
    this._mapService.fetchMaps()
      .subscribe(maps => this._maps = maps)
    ;
  }

  public add(name: string, url: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    url = url.trim();
    if (!url) {
      return;
    }
    this._mapService.createMap({name: name, url: url} as Map)
      .subscribe(map => {
        this._maps.push(map);
      })
    ;
  }

  public remove(map: Map): void {
    this._maps = this._maps.filter(item => item !== map);
    this._mapService.removeMap(map).subscribe();
  }

}
