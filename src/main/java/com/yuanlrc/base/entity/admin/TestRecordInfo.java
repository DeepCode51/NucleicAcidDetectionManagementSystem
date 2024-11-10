package com.yuanlrc.base.entity.admin;

public class TestRecordInfo {
    private String name;

    private String sex;

    private String address;

    private String cardNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "TestRecordInfo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

    public TestRecordInfo(String name, String sex, String address, String cardNumber) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.cardNumber = cardNumber;
    }

    public TestRecordInfo()
    {

    }
}
