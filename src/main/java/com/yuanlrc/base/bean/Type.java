package com.yuanlrc.base.bean;

/**
 * 户口类型
 */
public enum Type {

    COUNTRY(0, "农村"),
    CITY(1, "城市");

    public Integer code;

    public String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    Type(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Type getType(String type){
        if(type.equals(Type.CITY.value))
            return Type.CITY;
        else if(type.equals(Type.COUNTRY.value))
            return Type.COUNTRY;
        else
            return null;
    }

}
