import { Component, OnInit } from '@angular/core';
import { Deal } from '../core/models/deal.type';
import { GameProvider } from '../shared/services/GameProvider/game-provider';
import { Store } from '../core/models/store.type';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  public deal: Deal | null = null;
  public store: Store | null = null;

  constructor(private readonly gameProvider: GameProvider) { }

  ngOnInit() {
    this.chargeOneDeal();
    this.getStoreToShowImage();
  }

  private chargeOneDeal() {
    this.gameProvider.getDealsHoster()
      .subscribe({
        next: (deals) => {
          console.log(deals);
          this.deal = deals[0];
        },
        error: (err) => {
          console.error('Error fetching deals:', err);
        }
      });
  }
  private getStoreToShowImage() {
    this.gameProvider.getStores()
      .subscribe({
        next: (stores) => {
          console.log(stores);
          this.store = stores.filter(s => s.storeID === this.deal?.storeID)[0];
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

}
