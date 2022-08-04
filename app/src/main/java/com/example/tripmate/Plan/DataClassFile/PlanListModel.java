package com.example.tripmate.Plan.DataClassFile;

public class PlanListModel {
    String planCode;
    String planPlace;
    String planTitle;
    String planStart;
    String planEnd;

    public PlanListModel(String planCode, String planPlace, String planTitle, String planStart, String planEnd){
        this.planCode = planCode;
        this.planPlace = planPlace;
        this.planTitle = planTitle;
        this.planStart = planStart;
        this.planEnd = planEnd;
    }

    public String getPlanCode(){
        return planCode;
    }
    public void setPlanCode(String planCode){
        this.planCode = planCode;
    }
    public String getPlanPlace(){
        return planPlace;
    }
    public void setPlanPlace(String planPlace){
        this.planPlace = planPlace;
    }
    public String getPlanTitle(){
        return planTitle;
    }
    public void setPlanTitle(String planTitle){
        this.planTitle = planTitle;
    }
    public String getPlanStart(){
        return planStart;
    }
    public void setPlanStart(String planStart){
        this.planStart = planStart;
    }
    public String getPlanEnd(){
        return planEnd;
    }
    public void setPlanEnd(String planEnd){
        this.planEnd = planEnd;
    }
}
