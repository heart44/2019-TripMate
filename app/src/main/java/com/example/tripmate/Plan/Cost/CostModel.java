package com.example.tripmate.Plan.Cost;

public class CostModel {
    //String nickname;
    String costCode;
    String plancode;
    String costTitle;
    int costCategory;
    int costType;
    String costDate;
    int costPrice;

    public CostModel(String costCode, String plancode, String costTitle, int costCategory, int costType, String costDate, int costPrice){
        this.costCode = costCode;
        this.plancode = plancode;
        this.costTitle = costTitle;
        this.costCategory = costCategory;
        this.costType = costType;
        this.costDate = costDate;
        this.costPrice = costPrice;
    }

    /*public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }*/
    public String getCostCode() {
        return costCode;
    }
    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }
    public String getPlancode() {
        return plancode;
    }
    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }
    public int getCostCategory() {
        return costCategory;
    }
    public void setCostCategory(int costCategory) {
        this.costCategory = costCategory;
    }
    public String getCostTitle() {
        return costTitle;
    }
    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }
    public int getCostType() {
        return costType;
    }
    public void setCostType(int costType) {
        this.costType = costType;
    }
    public String getCostDate() {
        return costDate;
    }
    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }
    public int getCostPrice() {
        return costPrice;
    }
    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }
}
