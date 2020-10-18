package com.example.simple.bean;

public class BeanServiceType {
    /**
     * "serviceid": 3,
     * "serviceName": "预约养老",
     * "icon": "http://118.190.26.201:8080/mobileA/images/tubiao3.png",
     * "url": "https://baijiahao.baidu.com/s?id=1679501072086154559&wfr=spider&for=pc",
     * "serviceType": "智慧养老",
     * "desc": "jjj"
     * */
    private int serviceId;
    private String serviceName,icon,url,serviceType,desc;

    public BeanServiceType() {
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
