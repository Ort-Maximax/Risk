import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MapRoutingModule } from './map-routing.module';
import { MessageModule } from '../message';

import { MapDetailComponent } from './map-detail/map-detail.component';
import { MapListComponent } from './map-list/map-list.component';
import { MapSearchComponent } from './map-search/map-search.component';

@NgModule({
  declarations: [
    MapDetailComponent,
    MapListComponent,
    MapSearchComponent
  ],
  exports: [
    MapDetailComponent,
    MapListComponent,
    MapSearchComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MessageModule,
    RouterModule,
    // Last: routing module
    MapRoutingModule
  ]
})
export class MapModule { }
