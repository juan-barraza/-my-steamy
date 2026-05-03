package io.ionic.starter;

import android.graphics.Bitmap;

public class Store {
  private String storeID;
  private String storeName;
  private int isActive;
  private Images images;
  private Bitmap logoBitmap;


  public Store() {}
  public Store(String storeID, String storeName, int isActive, Images images) {
    this.storeID = storeID;
    this.storeName = storeName;
    this.isActive = isActive;
    this.images = images;
  }

  public String getStoreID() {
    return storeID;
  }

  public void setStoreID(String storeID) {
    this.storeID = storeID;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public int getIsActive() {
    return isActive;
  }

  public void setIsActive(int isActive) {
    this.isActive = isActive;
  }

  public Images getImages() {
    return images;
  }

  public void setImages(Images images) {
    this.images = images;
  }

  public Bitmap getLogoBitmap() {
    return logoBitmap;
  }

  public void setLogoBitmap(Bitmap logoBitmap) {
    this.logoBitmap = logoBitmap;
  }
}
