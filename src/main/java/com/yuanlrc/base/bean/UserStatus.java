package com.yuanlrc.base.bean;

public enum  UserStatus {
    NOT_PASS(0, "未通过"),
    PASS(1, "通过"),
    AUDIT(2, "审核中");

    private Integer code;

    private String value;

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

    UserStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
