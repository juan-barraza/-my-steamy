import { Injectable } from '@angular/core';
import { Preferences } from '@capacitor/preferences';
import { Deal } from '../../models/deal.type';


@Injectable({
  providedIn: 'root',
})
export class FavoriteService {

  constructor() { }

  async saveDealFavorite(deal: Deal): Promise<void> {
    await Preferences.set({
      key: 'deal',
      value: JSON.stringify(deal),
    });
  }

  async getDealFavorite(): Promise<Deal | null> {
    const { value } = await Preferences.get({key: 'deal'});
    if (!value) return null;

    return JSON.parse(value) as Deal;
  }

  async removeDealFavorite(): Promise<void> {
    await Preferences.remove({key: 'deal'});
  }

}
