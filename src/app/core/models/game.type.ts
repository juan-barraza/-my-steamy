export interface Game {
  info:              Info;
  cheapestPriceEver: CheapestPriceEver;
  deals:             Deal[];
}

export interface CheapestPriceEver {
  price: string;
  date:  number;
}

export interface Deal {
  storeID:     string;
  dealID:      string;
  price:       string;
  retailPrice: string;
  savings:     string;
}

export interface Info {
  title:      string;
  steamAppID: string;
  thumb:      string;
}
