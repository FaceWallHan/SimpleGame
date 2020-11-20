package com.example.simple.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class BeanParkRecord {

    private String id,carNum,charge,inTime,outTime,parkingid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid;
    }
    public static class inTimeAsc implements Comparator<BeanParkRecord> {
        @Override
        public int compare(BeanParkRecord t0, BeanParkRecord t1) {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            try {
                return Objects.requireNonNull(format.parse(t0.getInTime())).compareTo(format.parse(t1.getInTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }
    public static class outTimeDesc implements Comparator<BeanParkRecord> {
        @Override
        public int compare(BeanParkRecord t0, BeanParkRecord t1) {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            try {
                return -Objects.requireNonNull(format.parse(t0.getOutTime())).compareTo(format.parse(t1.getOutTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }
}
