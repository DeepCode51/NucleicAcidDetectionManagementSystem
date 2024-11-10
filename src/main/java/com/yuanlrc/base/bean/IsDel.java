package com.yuanlrc.base.bean;

/**
 * 是否被删除
 */
public enum IsDel {
    DEL(0, "已删除"),
    NOT_DEL(1, "未删除");

    private Integer code;
    private String value;

    IsDel(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
