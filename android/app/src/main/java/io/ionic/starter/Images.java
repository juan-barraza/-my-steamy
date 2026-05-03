package io.ionic.starter;

public class Images {
  private String banner;
  private String logo;
  private String icon;

  public  Images(){}
  public Images(String banner, String logo,  String icon)  {
    this.banner = banner;
    this.logo = logo;
    this.icon = icon;
  }


  public String getBanner() {
    return banner;
  }

  public void setBanner(String banner) {
    this.banner = banner;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
