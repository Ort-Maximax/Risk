import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { MapModule } from './map';
import { MessageModule } from './message';
import { PlayerModule } from './player';

import { AppComponent } from './app.component';
import { HubComponent } from './hub/hub.component';

@NgModule({
  declarations: [
    AppComponent,
    HubComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MapModule,
    MessageModule,
    PlayerModule,
    // Last: routing module
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
