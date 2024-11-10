package com.yuanlrc.base.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuanlrc.base.entity.admin.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	/**
	 * 按照用户名查找用户信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户id查询
	 * @param id
	 * @return
	 */
	@Query("select u from User u where id = :id")
	public User find(@Param("id")Long id);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_user set head_pic = :#{#user.headPic},username = :#{#user.username},password = :#{#user.password} where id = :#{#user.id}",nativeQuery = true)
	int updateUser(@Param("user") User user);

}
