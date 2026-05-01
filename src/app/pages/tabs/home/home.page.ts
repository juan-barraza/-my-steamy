import { Component, OnInit } from '@angular/core';
import { Deal } from '../../../core/models/deal.type';
import { GameProvider } from '../../../shared/services/GameProvider/game-provider';
import { Store } from '../../../core/models/store.type';
import { debounceTime, distinctUntilChanged, Subject, Subscription, switchMap } from 'rxjs';
import { ModalController } from '@ionic/angular';
import { DealModalComponent } from '../../../shared/components/deal-modal/deal-modal.component';
import { FavoriteService } from 'src/app/core/services/favorite-service/favorite-service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  public deals: Deal[] | [] = [];
  public stores: Store[] | [] = [];
  public isSearchingDeal: boolean = false;
  public isLoadingDeal: boolean = false;
  public favoriteDealId: string | undefined ;
  private searchSubjet = new Subject<string>();
  private searchSubscription!: Subscription;

  constructor(
    private readonly gameProvider: GameProvider,
    private readonly modalCtr: ModalController,
    private readonly favoriteService:FavoriteService,
  ) { }

  ngOnInit() {
    this.chargeDeals();
    this.getStores();
    this.searchSubscription = this.searchSubjet.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(query => {
        this.isLoadingDeal = true;
        if (query.trim() === '') {
          this.isSearchingDeal = false;
          return this.gameProvider.getDealsHoster();
        }
        this.isSearchingDeal = true;
        return this.gameProvider.getOffertsByTitle(query);
      })
    ).subscribe({
      next: (deals) => {
        this.deals = deals
        this.isLoadingDeal = false;
      },
      error: (err) => {
        console.error(err)
        this.isLoadingDeal = false;
      }
    })
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
    
    const {data, role} = await modal.onWillDismiss()
    if (role === 'viewdeal') {
      this.gameProvider.openDeal(data);
    }

  }

  getStoreLogo(storeID: string): string {
    const store = this.stores.find(s => s.storeID === storeID);
    return store ? 'https://www.cheapshark.com' + store.images.logo : '';
  }

  onSearchChange(event: any) {
    const query = event.detail.value ?? '';
    this.searchSubjet.next(query);
  }

  private chargeDeals() {
    this.gameProvider.getDealsHoster()
      .subscribe({
        next: (deals) => {
          console.log(deals);
          this.deals = deals;
        },
        error: (err) => {
          console.error('Error fetching deals:', err);
        }
      });
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

  async favoriteToggle(deal: Deal) {
    const currentDealFavorite = await this.favoriteService.getDealFavorite();
    if (currentDealFavorite?.dealID === deal.dealID) {
      await this.favoriteService.removeDealFavorite();
      this.favoriteDealId = undefined;
    } else {
      await this.favoriteService.saveDealFavorite(deal);
      this.favoriteDealId = deal.dealID;
    }
  }

  ngOnDestroy() {
    this.searchSubscription.unsubscribe();
  }

}
