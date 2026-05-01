import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Deal } from 'src/app/core/models/deal.type';
import { Store } from 'src/app/core/models/store.type';
import { FavoriteService } from 'src/app/core/services/favorite-service/favorite-service';
import { DealModalComponent } from 'src/app/shared/components/deal-modal/deal-modal.component';
import { GameProvider } from 'src/app/shared/services/GameProvider/game-provider';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.page.html',
  styleUrls: ['./favorites.page.scss'],
  standalone: false,
})
export class FavoritesPage implements OnInit {
  public favoriteDeal: Deal | null = null;
  private stores: Store[] | [] = [];

  constructor(
    private readonly favoriteService: FavoriteService,
    private readonly gameProvider: GameProvider,
    private readonly modalCtr: ModalController,
  ) { }

  ngOnInit() {
    this.getStores();
  }

  ionViewWillEnter() {
    this.getFavorite();
  }

  async onDetailDeal(deal: Deal) {
    const storeLogoUrl = this.getStoreLogo(deal.storeID);
    const modal = await this.modalCtr.create({
      component: DealModalComponent,
      componentProps: { deal, storeLogoUrl },
      breakpoints: [0, 0.90],
      initialBreakpoint: 0.90,
    });
    await modal.present();

    const { data, role } = await modal.onWillDismiss()
    if (role === 'viewdeal') {
      this.gameProvider.openDeal(data);
    }

  }
  getStoreLogo(storeID: string): string {
    const store = this.stores.find(s => s.storeID === storeID);
    return store ? 'https://www.cheapshark.com' + store.images.logo : '';
  }

  private getStores() {
    this.gameProvider.getStores()
      .subscribe({
        next: (stores) => {
          this.stores = stores;
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

  private async getFavorite() {
    this.favoriteDeal = await this.favoriteService.getDealFavorite();
  }
}
