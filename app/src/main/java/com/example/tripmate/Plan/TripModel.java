package com.example.tripmate.Plan;

public class TripModel {
    String title;
    String memo;

    public TripModel(String title, String memo){
        this.title = title;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }



}
