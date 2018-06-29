import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MessageModule } from '../message';
import { PlayerRoutingModule } from './player-routing.module';

import { PlayerDetailComponent } from './player-detail/player-detail.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { PlayerSearchComponent } from './player-search/player-search.component';

@NgModule({
  declarations: [
    PlayerDetailComponent,
    PlayerListComponent,
    PlayerSearchComponent
  ],
  exports: [
    PlayerDetailComponent,
    PlayerListComponent,
    PlayerSearchComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MessageModule,
    RouterModule,
    // Last: routing module
    PlayerRoutingModule
  ]
})
export class PlayerModule { }
