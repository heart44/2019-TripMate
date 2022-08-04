package com.example.tripmate.Board;

import java.util.Date;

public class BoardModel {
    private String boardCode;
    private String nickname;
    private String destination;
    private int gender;
    private int minage;
    private int maxage;
    private String purpose;
    private String matchdate;

    public BoardModel(String boardCode,String nickname, String destination, int gender, int minage, int maxage, String purpose,  String matchdate) {
        this.boardCode = boardCode;
        this.nickname = nickname;
        this.destination = destination;
        this.gender = gender;
        this.minage = minage;
        this.maxage = maxage;
        this.purpose = purpose;
        this.matchdate = matchdate;
    }

    public String getBoardCode() {
        return boardCode;
    }

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMinage() {
        return minage;
    }

    public void setMinage(int minage) { this.minage = minage; }

    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMatchdate() {
        return matchdate;
    }

    public void setMatchdate(String matchdate) {
        this.matchdate = matchdate;
    }
}
