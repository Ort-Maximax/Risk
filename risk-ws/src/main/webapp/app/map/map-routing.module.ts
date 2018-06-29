import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MapDetailComponent } from './map-detail/map-detail.component';
import { MapListComponent } from './map-list/map-list.component';

@NgModule({
  imports: [
    RouterModule.forChild(
      [
        { path: 'map/:id', component: MapDetailComponent },
        { path: 'map-list', component: MapListComponent },
      ]
    )
  ],
  exports: [
    RouterModule
  ]
})
export class MapRoutingModule { }
