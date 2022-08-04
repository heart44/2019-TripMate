package com.example.tripmate.Location;

public class LocationListViewItem implements Comparable<LocationListViewItem>{
    private String item_title;
    private String item_address1;
    private String item_firstimage; //썸네일 이미지
    private String item_tel;
    private int item_dist;

    private double item_mapx;
    private double item_mapy;

    public String getTitle(){
        return item_title;
    }
    public String getAddress1(){
        return item_address1;
    }
    public String getFirstimage(){
        return item_firstimage;
    }
    public String getTel(){
        return item_tel;
    }
    public int getDist(){return item_dist;}

    public double getItem_mapx(){return item_mapx;}
    public double getItem_mapy(){return item_mapy;}

    public void setTitle(String item_title){
        this.item_title = item_title;
    }
    public void setFirstimage(String item_firstimage){
        this.item_firstimage =item_firstimage;
    }
    public void setAddress1(String item_address1){
        this.item_address1 = item_address1;
    }
    public void setTel(String item_tel){
        this.item_tel = item_tel;
    }
    public void setDist(int item_dist){this.item_dist =item_dist;}

    public void setMapx(double item_mapx){this.item_mapx =item_mapx;}
    public void setMapy(double item_mapy){this.item_mapy =item_mapy;}

    @Override
    public int compareTo(LocationListViewItem o) {
        if(this.item_dist < o.item_dist)
            return -1;
        else if(o.item_dist < this.item_dist)
            return 1;
        return 0;
    }
}
