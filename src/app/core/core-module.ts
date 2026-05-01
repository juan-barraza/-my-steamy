import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpService } from './services/http/http';
import { FavoriteService } from './services/favorite-service/favorite-service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    HttpService,
    FavoriteService,
  ]
})
export class CoreModule { }
