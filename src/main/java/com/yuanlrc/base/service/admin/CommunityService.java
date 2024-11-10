package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.CommunityDao;
import com.yuanlrc.base.entity.admin.Community;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区 service
 */
@Service
public class CommunityService {

    @Autowired
    private CommunityDao communityDao;

    public Community find(Long id)
    {
        return communityDao.find(id);
    }

    /**
     * 判断是否存在
     * @param community
     * @return
     */
    public Boolean isExist(Community community,Long id){
        Community com = communityDao.findByCityAndAreaAndStreetAndName
                (community.getCity(), community.getArea(), community.getStreet(), community.getName());
        if(com != null){
            if(com.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    public Community save(Community entity)
    {
        return communityDao.save(entity);
    }

    public void delete(Long id)
    {
        communityDao.deleteById(id);
    }

    public List<Community> findAll()
    {
        return communityDao.findAll();
    }

    public PageBean<Community> findList(Community community, PageBean<Community> pageBean)
    {
        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Community> example = Example.of(community, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Community> findAll = communityDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }
}
