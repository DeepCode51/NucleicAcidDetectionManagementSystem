package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.admin.TestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 核酸检测Dao
 */
@Repository
public interface TestRecordDao extends JpaRepository<TestRecord,Long>, JpaSpecificationExecutor<TestRecord> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select t from TestRecord t where id = :id")
    TestRecord find(@Param("id")Long id);

    /**
     * 根据身份证号查询
     * @param cardNumber
     * @return
     */
    List<TestRecord> findByCardNumberOrderByCreateTimeDesc(String cardNumber);

    /**
     * 根据社区id查询
     * @param communityId
     * @return
     */
    List<TestRecord> findByCommunityId(Long communityId);


    /**
     * 根据身份证号和社区查询
     * @param cardNumber
     * @return
     */
    int countByCardNumberAndCommunityIdAndGatherTimeGreaterThanEqualAndStatus(String cardNumber, Long communityId, Date gatherTime, UserStatus userStatus);
    

}
