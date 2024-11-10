package com.yuanlrc.base.bean;

/**
 * 户口类型
 */
public enum UserType {

    SUPER_ADMIN(1l, "超级管理员"),
    ADMIN(2l, "普通管理员");

    public Long code;

    public String value;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    UserType(Long code, String value) {
        this.code = code;
        this.value = value;
    }
}
