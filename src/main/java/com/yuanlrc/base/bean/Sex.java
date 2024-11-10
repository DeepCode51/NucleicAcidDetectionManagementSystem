package com.yuanlrc.base.bean;

/**
 * 性别
 */
public enum Sex {

    FEMALE(0, "女性"),
    MALE(1, "男性");

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


    Sex(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (Sex sex : values()) {
            if(sex.getCode().equals(code)){
                return sex.getValue();
            }
        }
        return null;
    }

    public static Sex getSex(String sex)
    {
        if(sex.equals(Sex.FEMALE.value))
            return Sex.FEMALE;
        else if(sex.equals(Sex.MALE.value))
            return Sex.MALE;
        else
            return null;
    }
}
