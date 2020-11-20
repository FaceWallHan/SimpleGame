package com.example.simple.bean;

import java.io.Serializable;
import java.util.Comparator;

public class BeanParkInfo implements Serializable {
    private String parkingid;
    private String parkName;
    private String spaceNum;
    private String address;
    private String rate;
    private int distance;
    private String isOpen;
    private String surCarPort;

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(String spaceNum) {
        this.spaceNum = spaceNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getSurCarPort() {
        return surCarPort;
    }

    public void setSurCarPort(String surCarPort) {
        this.surCarPort = surCarPort;
    }

    public String getRateRefer() {
        return rateRefer;
    }

    public void setRateRefer(String rateRefer) {
        this.rateRefer = rateRefer;
    }

    private String rateRefer;
    public static class DescDistance implements Comparator<BeanParkInfo> {
        @Override
        public int compare(BeanParkInfo info, BeanParkInfo t1) {
            return info.getDistance()-(t1.getDistance());
        }
    }
}
