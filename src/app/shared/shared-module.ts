import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameProvider } from './services/GameProvider/game-provider';
import { CardComponent } from './components/CardComponent/card-component.component';
import { IonicModule } from '@ionic/angular';



@NgModule({
  declarations: [
    CardComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
  ],
  providers: [
    GameProvider
  ],
  exports: [
    CardComponent
  ]
})
export class SharedModule { }
