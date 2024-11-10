package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.entity.admin.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 常驻人口Dao
 */
@Repository
public interface ResidentDao extends JpaRepository<Resident,Long> , JpaSpecificationExecutor<Resident>{

    /**
     * 根据id查询
     * @param id
     * @return
     */

    @Query("select r from Resident r where r.id = :id")
    Resident find(@Param("id")Long id);

    /**
     * 根据身份证号和社区查询有效信息
     * @param cardNumber
     * @param city
     * @return
     */
    Resident findByCardNumberAndCommunityCityAndIsDel(String cardNumber, String city, IsDel isdel);

    /**
     * 根据身份证号查询当前有效信息
     * @param cardNumber
     * @param isdel
     * @return
     */
    Resident findByCardNumberAndIsDel(String cardNumber, IsDel isdel);

    /**
     * 根据社区查询
     * @param communityId
     * @param isdel
     * @return
     */
    List<Resident> findByCommunityIdAndIsDel(Long communityId,IsDel isdel);

    /**
     * 根据身份证号和社区id查询有效信息
     * @param cardNumber
     * @param communityId
     * @return
     */
    Resident findByCardNumberAndCommunityIdAndIsDel(String cardNumber, Long communityId, IsDel isdel);

    /**
     * 查询全部社区有效信息
     * @param isdel
     * @return
     */
    List<Resident> findByIsDel(IsDel isdel);

    /**
     * 根据社区id  有效 不在身份证list中的
     * @param communityId
     * @param isdel
     * @param cardNumber
     * @return
     */
    List<Resident> findByCommunityIdAndIsDelAndCardNumberNotIn(Long communityId, IsDel isdel,List<String> cardNumber);
}
