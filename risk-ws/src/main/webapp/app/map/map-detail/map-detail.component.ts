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
import { MapService } from '../map.service';

import { Map } from '../map.model';

@Component({
  selector: 'app-map-detail',
  templateUrl: './map-detail.component.html',
  styleUrls: ['./map-detail.component.css']
})
export class MapDetailComponent implements OnInit {

  private _map$: Observable<Map>;

  public constructor(
    private _activatedRoute: ActivatedRoute,
    private _location: Location,
    private _messageService: MessageService,
    private _mapService: MapService
  ) { }

  public ngOnInit(): void {
    this._map$ = this._activatedRoute.paramMap.pipe(
      switchMap(paramMap => this._mapService.fetchMap(+paramMap.get('id'))),
      catchError(error => {
        this._messageService.add(error);
        return throwError(error);
      })
    );
  }

  public get map$(): Observable<Map> {
    return this._map$;
  }

  public back(): void {
    this._location.back();
  }

  public save(map: Map): void {
    this._mapService.updateMap(map).subscribe();
    this._location.back();
  }

}
