import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HubComponent } from './hub/hub.component';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        { path: 'hub', component: HubComponent },
        { path: '', pathMatch: 'full', redirectTo: '/hub' },
        { path: '**', redirectTo: '/hub' }
      ]
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
