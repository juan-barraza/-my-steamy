import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameProvider } from './services/GameProvider/game-provider';
import { CardComponent } from './components/CardComponent/card-component.component';
import { IonicModule } from '@ionic/angular';
import { DealModalComponent } from './components/deal-modal/deal-modal.component';



@NgModule({
  declarations: [
    CardComponent,
    DealModalComponent,
  ],
  imports: [
    CommonModule,
    IonicModule,
  ],
  providers: [
    GameProvider
  ],
  exports: [
    CardComponent,
    DealModalComponent,
  ]
})
export class SharedModule { }
