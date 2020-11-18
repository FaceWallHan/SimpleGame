package com.example.simple.bean;

import java.io.Serializable;

public class BeanAttend implements Serializable {
    private String num;
    private String hospitalId;
    private String departmentId;
    private String time;
    private String type;
    private String doctorId;
    private static String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public static void setDepartmentName(String departmentName) {
        BeanAttend.departmentName = departmentName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
