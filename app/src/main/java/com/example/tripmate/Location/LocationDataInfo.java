package com.example.tripmate.Location;

public class LocationDataInfo {
    private String address1; //주소
    private String address2; //상세주소
    private double mapX; //gps X 좌표
    private double mapY; //gps Y 좌표
    private String title; //콘텐츠명(제목)
    private String tel;

    private String firstimage; //원본 이미지
    private String firstimage2; //썸네일 이미지
    private String ContentTypeID; //콘텐츠 타입 ID
    private int dist; //중심 좌표로 부터의 거리

    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getFirstimage() {
        return firstimage;
    }
    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }
    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    public double getMapX() {
        return mapX;
    }
    public void setMapX(double mapX) {
        this.mapX = mapX;
    }
    public double getMapY() {
        return mapY;
    }
    public void setMapY(double mapY) {
        this.mapY = mapY;
    }

    public String getContentTypeID() {
        return ContentTypeID;
    }
    public void setContentTypeID(String ContentTypeID) {
        this.ContentTypeID = ContentTypeID;
    }

    public int getDist(){
        return dist;
    }
    public void setDist(int dist){
        this.dist = dist;
    }

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getTel(){return tel;}
    public void setTel(String tel){
        this.tel = tel;
    }

}
