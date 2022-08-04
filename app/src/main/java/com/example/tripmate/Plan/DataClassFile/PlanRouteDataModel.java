package com.example.tripmate.Plan.DataClassFile;

public class PlanRouteDataModel {

    String userid;
    String plancode;
    String plandate;
    String title;
    double locationx;
    double locationy;
    String memo;
    int index;
    int totalPrice;
    private int itemViewType;

    public PlanRouteDataModel() {}

    public PlanRouteDataModel(String userid, String plancode, String plandate, String title, double locationx, double locationy, String memo, int index/*, int totalPrice*/) {
        this.userid = userid;
        this.plancode = plancode;
        this.plandate = plandate;
        this.title = title;
        this.locationx = locationx;
        this.locationy = locationy;
        this.memo = memo;
        this.index = index;
        //this.totalPrice = totalPrice;
    }

    public void setItem(String userid, String plancode, String title, double locationx, double locationy) {
        this.userid = userid;
        this.plancode = plancode;
        this.locationx = locationx;
        this.locationy = locationy;
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLocationx() {
        return locationx;
    }

    public void setLocationx(double locationx) {
        this.locationx = locationx;
    }

    public double getLocationy() {
        return locationy;
    }

    public void setLocationy(double locationy) {
        this.locationy = locationy;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }
}
