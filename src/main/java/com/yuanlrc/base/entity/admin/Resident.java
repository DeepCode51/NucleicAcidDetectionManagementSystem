package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.Sex;
import com.yuanlrc.base.bean.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 常驻人口
 */
@Entity
@Table(name="ylrc_resident")
@EntityListeners(AuditingEntityListener.class)
public class Resident extends BaseEntity {

    @Column(name = "account_number")
    private Integer accountNumber;//户号

    @Column(name = "address")
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 200,
            errorRequiredMsg = "请输入本户地址", errorMinLengthMsg = "本户地址必须在2~200个字符之间", errorMaxLengthMsg = "本户地址必须在2~200个字符之间")
    private String address; //本户地址

    @Column(name = "name", length = 18)
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入姓名", errorMinLengthMsg = "姓名必须在2~18个字符之间", errorMaxLengthMsg = "姓名必须在2~18个字符之间")
    private String name; //姓名

    @Column(name = "sex", nullable = false, length = 10)
    private Integer sex = Sex.FEMALE.getCode();

    @Column(name = "card_number", length = 32)
    @ValidateEntity(required = true, errorRequiredMsg = "请输入身份证号码")
    private String cardNumber; //身份证号码

    @Enumerated
    @Column(name = "type")
    private Type type;//户口类型

    @ValidateEntity(required = true,requiredLeng = true, minLength = 2, maxLength = 10,
            errorRequiredMsg = "请输入与户主关系", errorMinLengthMsg = "与户主关系必须在2~10个字符之间", errorMaxLengthMsg = "与户主关系必须在2~10个字符之间")
    @Column(name = "relation")
    private String relation;//与户主关系

    @JoinColumn(name = "community_id")
    @ManyToOne
    private Community community; //所属社区

    @Column(name = "is_del", nullable = false, length = 10)
    @Enumerated
    private IsDel isDel = IsDel.NOT_DEL; //是否删除

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    @Override
    public String toString() {
        return "Resident{" +
                "accountNumber=" + accountNumber +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", cardNumber='" + cardNumber + '\'' +
                ", type=" + type +
                ", relation='" + relation + '\'' +
                ", community=" + community +
                ", isDel=" + isDel +
                '}';
    }
}
