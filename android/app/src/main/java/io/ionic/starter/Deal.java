package io.ionic.starter;

public class Deal {
  private String dealID;
  private String gameID;
  private String storeID;
  private String salePrice;
  private String normalPrice;
  private String savings;
  private String title;
  private String thumb;

  public Deal () {}
  public Deal(
    String dealID,
    String gameID,
    String storeID,
    String salePrice,
    String normalPrice,
    String savings,
    String title,
    String thumb) {
    this.dealID = dealID;
    this.gameID = gameID;
    this.storeID = storeID;
    this.salePrice = salePrice;
    this.normalPrice = normalPrice;
    this.savings = savings;
    this.title = title;
    this.thumb = thumb;
  }


  public String getDealID() {
    return dealID;
  }

  public void setDealID(String dealID) {
    this.dealID = dealID;
  }

  public String getGameID() {
    return gameID;
  }

  public void setGameID(String gameID) {
    this.gameID = gameID;
  }

  public String getStoreID() {
    return storeID;
  }

  public void setStoreID(String storeID) {
    this.storeID = storeID;
  }

  public String getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(String salePrice) {
    this.salePrice = salePrice;
  }

  public String getNormalPrice() {
    return normalPrice;
  }

  public void setNormalPrice(String normalPrice) {
    this.normalPrice = normalPrice;
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

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }
}

