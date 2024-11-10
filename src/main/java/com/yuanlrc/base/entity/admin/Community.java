package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 社区表
 */
@Entity
@Table(name="ylrc_community")
@EntityListeners(AuditingEntityListener.class)
public class Community extends BaseEntity {

    @Column(name = "province")
    @ValidateEntity(required = true, errorRequiredMsg = "请输入省份")
    private String province; //省份

    @Column(name = "city")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择城市")
    private String city; //城市

    @Column(name = "area")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择区")
    private String area; //区

    @Column(name = "street")
    @ValidateEntity(required = true,requiredLeng = true,minLength = 2, maxLength = 100,
            errorRequiredMsg = "请输入街道/镇名称", errorMinLengthMsg = "街道/镇名称必须在2~100个字符之间", errorMaxLengthMsg = "街道/镇名称必须在2~100个字符之间")
    private String street; //街道

    @Column(name = "name")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 100,
            errorRequiredMsg = "请输入具体地址/社区名称",errorMinLengthMsg = "具体地址/社区名称必须在2~100个字符之间", errorMaxLengthMsg = "具体地址/社区名称必须在2~100个字符之间")
    private String name; //社区名称

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    @Override
    public String toString() {
        return "Community{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
