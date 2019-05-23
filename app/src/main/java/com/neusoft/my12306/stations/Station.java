package com.neusoft.my12306.stations;

public class Station {
  // _id ,station_name,sort_order,tele_code,province,city
    private String id;
    private String stationName;
    private String sortOrder;
    private String teleCode;
    private String province;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTeleCode() {
        return teleCode;
    }

    public void setTeleCode(String teleCode) {
        this.teleCode = teleCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", stationName='" + stationName + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", teleCode='" + teleCode + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

