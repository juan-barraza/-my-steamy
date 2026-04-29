import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameProvider } from './services/GameProvider/game-provider';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    GameProvider
  ]
})
export class SharedModule { }
