package com.pms.Model;

public class DoneStoreListItems {

    public String shopID;
    public String beatID;
    public String doneShopName;
    public String doneAddress;
    public String doneColor;
    public String status;

    public DoneStoreListItems() {
    }

    public DoneStoreListItems(String shopName  , String Address , String color) {

        this.doneShopName = shopName;
        this.beatID = beatID;
        this.doneAddress = Address;
        this.doneColor = color;
    }

    public String getshopID() {
        return shopID;
    }
    public void setshopID(String shopID) {
        this.shopID = shopID;
    }

    public String getbeatID() {
        return beatID;
    }

    public void setbeatID(String beatID) {
        this.beatID = beatID;
    }

    public String getdoneShopName() {
        return doneShopName;
    }

    public void setdoneShopName(String doneShopName) {
        this.doneShopName = doneShopName;
    }

    public String getdoneAddress() {
        return doneAddress;
    }
    public void setdoneAddress(String doneAddress) {
        this.doneAddress = doneAddress;
    }

    public String getdoneColor() {
        return doneColor;
    }
    public void setdoneColor(String doneColor) {
        this.doneColor = doneColor;
    }
}
