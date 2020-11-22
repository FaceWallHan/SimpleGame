package com.example.simple.bean;

public class BeanBus {
    private String busid,pathName,startSite,endSite,runTime1,runTime2,price,mileage;

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getStartSite() {
        return startSite;
    }

    public void setStartSite(String startSite) {
        this.startSite = startSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public String getRunTime1() {
        return runTime1;
    }

    public void setRunTime1(String runTime1) {
        this.runTime1 = runTime1;
    }

    public String getRunTime2() {
        return runTime2;
    }

    public void setRunTime2(String runTime2) {
        this.runTime2 = runTime2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        //里程
        this.mileage = mileage;
    }
}
