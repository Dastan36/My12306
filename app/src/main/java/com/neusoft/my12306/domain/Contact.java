package com.neusoft.my12306.domain;

/**
 * @Author: jshand
 * @Date: 2019/5/15 10:45
 * @site https://github.com/jshand
 * @Project ssm-parent
 * @Version 1.0
 */
public class Contact {

    private String name;
    private String idType;
    private String idNo;
    private String telphone;


    public Contact() {
    }

    public Contact(String name, String idType, String idNo, String telphone) {
        this.name = name;
        this.idType = idType;
        this.idNo = idNo;
        this.telphone = telphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", idType='" + idType + '\'' +
                ", idNo='" + idNo + '\'' +
                ", telphone='" + telphone + '\'' +
                '}';
    }
}
