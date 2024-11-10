package com.yuanlrc.base.entity.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.Sex;
import com.yuanlrc.base.bean.UserStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * 流动人口
 */
@Entity
@Table(name="ylrc_migrant")
@EntityListeners(AuditingEntityListener.class)
public class Migrant extends BaseEntity {

    @Column(name = "address")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 200,
            errorRequiredMsg = "请输入详细地址", errorMinLengthMsg = "详细地址必须在2~200个字符之间", errorMaxLengthMsg = "详细地址必须在2~200个字符之间")
    private String address; //详细地址

    @Column(name = "name", length = 18)
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入姓名", errorMinLengthMsg = "姓名必须在2~18个字符之间", errorMaxLengthMsg = "姓名必须在2~18个字符之间")
    private String name; //姓名

    @Column(name = "sex", nullable = false, length = 10)
    private Integer sex = Sex.FEMALE.getCode();

    @Column(name = "card_number", length = 32)
    @ValidateEntity(required = true, errorRequiredMsg = "请输入身份证号码")
    private String cardNumber; //身份证号码

    @Column(name = "census_address")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 200,
            errorRequiredMsg = "请输入户籍所在地", errorMinLengthMsg = "户籍所在地必须在2~200个字符之间",
            errorMaxLengthMsg = "户籍所在地必须在2~200个字符之间")
    private String censusAddress; //户籍所在地

    @JoinColumn(name = "community_id")
    @ManyToOne
    private Community community; //所属社区

    @Column(name = "is_del", nullable = false, length = 10)
    @Enumerated
    private IsDel isDel = IsDel.NOT_DEL; //是否删除

    //是否通过
    @Column(name = "status", nullable = false, length = 10)
    @Enumerated
    private UserStatus status = UserStatus.PASS;

    @Column(name = "reasons")
    private String reasons; //未通过理由

    @Column(name = "card_img")
    @Lob
    private String cardImg; //两张身份证图片

    @Transient
    private ArrayList<String> images = new ArrayList<>();

    /**
     * 图片string转 arraylist
     */
    public void headPicToImages()
    {
        this.images.clear();
        JSONArray jsonImg = JSON.parseArray(this.cardImg);
        for(int i=0; i<jsonImg.size(); i++)
        {
            String img = "/photo/view?filename=" + jsonImg.getString(i);
            this.images.add(img);
        }
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

    public String getCensusAddress() {
        return censusAddress;
    }

    public void setCensusAddress(String censusAddress) {
        this.censusAddress = censusAddress;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public IsDel getIsDel() {
        return isDel;
    }

    public void setIsDel(IsDel isDel) {
        this.isDel = isDel;
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public UserStatus getStatus() { return status; }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Migrant{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", cardNumber='" + cardNumber + '\'' +
                ", censusAddress='" + censusAddress + '\'' +
                ", community=" + community +
                ", isDel=" + isDel +
                ", status=" + status +
                ", reasons='" + reasons + '\'' +
                ", cardImg='" + cardImg + '\'' +
                '}';
    }
}
