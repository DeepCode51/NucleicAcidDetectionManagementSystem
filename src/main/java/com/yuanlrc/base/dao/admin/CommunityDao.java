package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 社区dao
 */
@Repository
public interface CommunityDao extends JpaRepository<Community, Long> {

    @Query("select c from Community c where c.id = :id")
    Community find(@Param("id")Long id);

    Community findByCityAndAreaAndStreetAndName(String city,String area,String street,String name);
}
