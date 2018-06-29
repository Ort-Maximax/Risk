import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlayerDetailComponent } from './player-detail/player-detail.component';
import { PlayerListComponent } from './player-list/player-list.component';

@NgModule({
  imports: [
    RouterModule.forChild(
      [
        { path: 'player/:id', component: PlayerDetailComponent },
        { path: 'player-list', component: PlayerListComponent },
      ]
    )
  ],
  exports: [
    RouterModule
  ]
})
export class PlayerRoutingModule { }
