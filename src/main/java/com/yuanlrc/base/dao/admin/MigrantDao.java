package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.admin.Migrant;
import com.yuanlrc.base.entity.admin.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流动人口dao
 */
@Repository
public interface MigrantDao extends JpaRepository<Migrant, Long>, JpaSpecificationExecutor<Migrant> {

    @Query("select c from Migrant c where c.id = :id")
    Migrant find(@Param("id")Long id);

    /**
     * 根据身份证号和社区查询有效信息
     * @param cardNumber
     * @param city
     * @return
     */
    Migrant findByCardNumberAndCommunityCityAndIsDel(String cardNumber, String city, IsDel isdel);

    /**
     * 根据身份证号查询当前有效信息
     * @param cardNumber
     * @param isdel
     * @return
     */
    Migrant findByCardNumberAndIsDel(String cardNumber, IsDel isdel);


    /**
     * 根据身份证号查询当前有效信息
     * @param cardNumber
     * @param isdel
     * @return
     */
    Migrant findByCardNumberAndIsDelAndStatus(String cardNumber, IsDel isdel,UserStatus userStatus);

    /**
     * 根据社区查询
     * @param communityId
     * @param isdel
     * @return
     */
    List<Migrant> findByCommunityIdAndIsDel(Long communityId,IsDel isdel);


    /**
     * 找到身份证号码是小区通过和未删除的人
     * @param cardNumber
     * @param communityId
     * @param isDel
     * @param userStatus
     * @return
     */
    Migrant findByCardNumberAndCommunityIdAndIsDelAndStatus(String cardNumber, Long communityId, IsDel isDel, UserStatus userStatus);

    /**
     * 找到这个小区通过和未删除的
     * @param communityId
     * @param isdel
     * @param userStatus
     * @return
     */
    List<Migrant> findByCommunityIdAndIsDelAndStatus(Long communityId, IsDel isdel, UserStatus userStatus);

    /**
     * 查询所有有效的信息
     * @param isdel
     * @param userStatus
     * @return
     */
    List<Migrant> findByIsDelAndStatus(IsDel isdel, UserStatus userStatus);

    List<Migrant> findByCommunityIdAndIsDelAndStatusAndCardNumberNotIn(Long communityId, IsDel isdel,UserStatus userStatus, List<String> cardNumber);

}
