package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.Sex;
import com.yuanlrc.base.bean.UserStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.naming.Name;
import javax.persistence.*;
import java.util.Date;

/**
 * 检测记录表
 */
@Entity
@Table(name = "ylrc_test_record")
@EntityListeners(AuditingEntityListener.class)
public class TestRecord extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;//社区

    @Column(name = "address")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 100,
            errorRequiredMsg = "请输入地址", errorMinLengthMsg = "地址必须在2~100个字符之间", errorMaxLengthMsg = "地址必须在2~100个字符之间")
    private String address; //地址

    @Column(name = "name", length = 18)
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入姓名", errorMinLengthMsg = "姓名必须在2~18个字符之间", errorMaxLengthMsg = "姓名必须在2~18个字符之间")
    private String name; //姓名

    @Column(name = "card_number", length = 32)
    @ValidateEntity(required = true, errorRequiredMsg = "请输入身份证号码")
    private String cardNumber; //身份证号码

    @Column(name = "sex", nullable = false, length = 10)
    private Integer sex = Sex.FEMALE.getCode();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "gather_time")
    private Date gatherTime;//采集时间

    @ValidateEntity(required = true,requiredLeng = true,minLength = 2,maxLength = 100,
            errorRequiredMsg="采集地点不能为空!",errorMinLengthMsg="采集地点须在2~100个字符之间!",errorMaxLengthMsg="采集地点须在2~100个字符之间!!")
    @Column(name = "gather_address")
    private String gatherAddress;//采集地点

    @ValidateEntity(required = true,requiredLeng = true,errorRequiredMsg = "请输入采集管号",minLength=6,maxLength=32,
            errorMinLengthMsg="采集管号须在6~32个字符之间!",errorMaxLengthMsg="采集管号须在6~32个字符之间!")
    @Column(name = "gather_number")
    private String gatherNumber;//采集管号

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,
            errorRequiredMsg="采集人姓名不能为空!",errorMinLengthMsg="采集人姓名须在2~18个字符之间!",errorMaxLengthMsg="采集人姓名须在2~18个字符之间!")
    @Column(name = "gather_name")
    private String gatherName;//采集人姓名

    @ValidateEntity(required=true,errorRequiredMsg = "请输入采集人电话")
    @Column(name="gather_mobile",length=12)
    private String gatherMobile;//采集人电话

    @Column(name = "img")
    private String img; //核酸图片

    //是否通过
    @Column(name = "status", nullable = false, length = 10)
    @Enumerated
    private UserStatus status = UserStatus.PASS;//审批状态

    @Column(name = "reasons")
    private String reasons; //未通过理由

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Date gatherTime) {
        this.gatherTime = gatherTime;
    }

    public String getGatherAddress() {
        return gatherAddress;
    }

    public void setGatherAddress(String gatherAddress) {
        this.gatherAddress = gatherAddress;
    }

    public String getGatherNumber() {
        return gatherNumber;
    }

    public void setGatherNumber(String gatherNumber) {
        this.gatherNumber = gatherNumber;
    }

    public String getGatherName() {
        return gatherName;
    }

    public void setGatherName(String gatherName) {
        this.gatherName = gatherName;
    }

    public String getGatherMobile() {
        return gatherMobile;
    }

    public void setGatherMobile(String gatherMobile) {
        this.gatherMobile = gatherMobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "TestRecord{" +
                "community=" + community +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", sex=" + sex +
                ", gatherTime=" + gatherTime +
                ", gatherAddress='" + gatherAddress + '\'' +
                ", gatherNumber='" + gatherNumber + '\'' +
                ", gatherName='" + gatherName + '\'' +
                ", gatherMobile='" + gatherMobile + '\'' +
                ", img='" + img + '\'' +
                ", status=" + status +
                ", reasons='" + reasons + '\'' +
                '}';
    }
}
