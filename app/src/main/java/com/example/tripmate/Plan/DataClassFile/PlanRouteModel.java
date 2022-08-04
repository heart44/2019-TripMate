package com.example.tripmate.Plan.DataClassFile;

public class PlanRouteModel {

    private String planCode;
    private String planPlace;
    private String planTitle;
    private String startDate;
    private String endDate;
    private int index;

    public PlanRouteModel() {}

    public PlanRouteModel(String planPlace, String planTitle, String startDate, String endDate) {
        this.planPlace = planPlace;
        this.planTitle = planTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getPlanCode() {
        return planCode;
    }
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
    public String getPlanPlace() {
        return planPlace;
    }
    public void setPlanPlace(String planPlace) {
        this.planPlace = planPlace;
    }
    public String getPlanTitle() {
        return planTitle;
    }
    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
