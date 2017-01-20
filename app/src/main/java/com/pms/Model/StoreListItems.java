package com.pms.Model;

public class StoreListItems {

    public String shopID;
    public String beatID;
    public String shopName;
    public String Address;
    public String color;
    public String status;
    public String designation;

    public StoreListItems() {
    }

    public StoreListItems(String shopID, String beatID, String shopName  , String Address , String color , String status) {

        this.shopID = shopID;
        this.shopName = shopName;
        this.beatID = beatID;
        this.Address = Address;
        this.color = color;
        this.status = status;

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

    public String getshopName() {
        return shopName;
    }

    public void setshopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getcolor() {
        return color;
    }
    public void setcolor(String color) {
        this.color = color;
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }


}
