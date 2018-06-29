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

 import { MapService } from '../map.service';

 import { Map } from '../map.model';

@Component({
  selector: 'app-map-search',
  templateUrl: './map-search.component.html',
  styleUrls: [ './map-search.component.css' ]
})
export class MapSearchComponent implements OnInit {
  private _maps$: Observable<Map[]>;
  private _subject = new Subject<string>();

  public constructor(
    private _mapService: MapService
  ) {}

  public ngOnInit(): void {
    this._maps$ = this._subject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this._mapService.searchMaps(term))
    );
  }

  public get maps$() {
    return this._maps$;
  }

  public next(term: string): void {
    this._subject.next(term);
  }

}
