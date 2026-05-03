package io.ionic.starter;

public class GameDeal {
  private String storeID;
  private String dealID;
  private String price;
  private String retailPrice;
  private String savings;
  private String title;

  public GameDeal() {}
  public GameDeal(String storeID, String dealID, String price, String retailPrice, String savings, String title) {
    this.storeID = storeID;
    this.dealID = dealID;
    this.price = price;
    this.retailPrice = retailPrice;
    this.savings = savings;
    this.title = title;
  }

  public String getStoreID() {
    return storeID;
  }

  public void setStoreID(String storeID) {
    this.storeID = storeID;
  }

  public String getDealID() {
    return dealID;
  }

  public void setDealID(String dealID) {
    this.dealID = dealID;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(String retailPrice) {
    this.retailPrice = retailPrice;
  }

  public String getSavings() {
    return savings;
  }

  public void setSavings(String savings) {
    this.savings = savings;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
