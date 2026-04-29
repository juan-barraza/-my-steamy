import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Deal } from 'src/app/core/models/deal.type';
import { HttpService } from 'src/app/core/services/http/http';
import { Store } from 'src/app/core/models/store.type';
import { environment } from 'src/environments/environment';
import { HttpParams } from '@angular/common/http';
import { Browser } from "@capacitor/browser";

@Injectable({
  providedIn: 'root',
})
export class GameProvider {
  private redirectUrl: string = environment.API_URL_REDIRECT;


  constructor(private readonly httpService: HttpService) { }

  getDealsHoster(): Observable<Deal[]> {
    return this.httpService.get<Deal[]>('/deals?pageSize=5');
  }

  getStores(): Observable<Store[]> {
    return this.httpService.get<Store[]>('/stores');
  }

  getOffertsByTitle(title: string): Observable<Deal[]> {
    const titleParam = new HttpParams().set('title', title);
    return this.httpService.get<Deal[]>(`/deals`, titleParam);
  }

  async openDeal(dealID: string): Promise<void> {
    await Browser.open({ url: `${this.redirectUrl}/redirect?dealID=${dealID}` });
  }
}
