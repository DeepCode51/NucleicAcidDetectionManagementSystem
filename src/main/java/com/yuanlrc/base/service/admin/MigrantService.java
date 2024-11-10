package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Sex;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.dao.admin.MigrantDao;
import com.yuanlrc.base.entity.admin.Migrant;
import com.yuanlrc.base.entity.admin.Resident;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;

/**
 * 流动人口 service
 */
@Service
public class MigrantService {

    @Autowired
    private MigrantDao migrantDao;

    public Migrant find(Long id) {
        return migrantDao.find(id);
    }


    public Migrant save2(Migrant entity) {
        return migrantDao.save(entity);
    }


    /**
     * 根据社区查询
     *
     * @param communityId
     * @param isdel
     * @return
     */
    public List<Migrant> findByCommunityIdAndIsDels(Long communityId, IsDel isdel) {
        return migrantDao.findByCommunityIdAndIsDel(communityId, isdel);
    }

    @Transactional
    public Migrant save(Migrant entity) {
        Migrant byCardNumberAndIsDel = migrantDao.findByCardNumberAndIsDel(entity.getCardNumber(), IsDel.NOT_DEL);
        if (byCardNumberAndIsDel != null) {
            byCardNumberAndIsDel.setIsDel(IsDel.DEL);
            migrantDao.save(byCardNumberAndIsDel);
        }
        return migrantDao.save(entity);
    }

    public void delete(Long id) {
        migrantDao.deleteById(id);
    }

    public List<Migrant> findAll() {
        return migrantDao.findAll();
    }

    public PageBean<Migrant> findList(Migrant entity, PageBean<Migrant> pageBean, Long communityId) {
        Specification<Migrant> specification = new Specification<Migrant>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Migrant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("name"), entity.getName() == null ? "%%" : "%" + entity.getName() + "%");

                Predicate community = criteriaBuilder.equal(root.get("community"), communityId);

                predicate = criteriaBuilder.and(predicate, community);

                Predicate isDel = criteriaBuilder.equal(root.get("isDel"), IsDel.NOT_DEL);

                predicate = criteriaBuilder.and(predicate, isDel);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "status");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Migrant> findAll = migrantDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        for (Migrant item : pageBean.getContent()) {
            if (!StringUtils.isEmpty(item.getCardImg()))
                item.headPicToImages();
        }

        return pageBean;
    }

    public String getSex(Integer sex) {
        if (sex == Sex.FEMALE.getCode())
            return "女性";
        else
            return "男性";
    }


    @Transactional
    public void save(HashMap<String, Migrant> migrantHashMap) {
        for (String key : migrantHashMap.keySet()) {
            save(migrantHashMap.get(key));
        }
    }

    /**
     * 通过身份证号码查找
     *
     * @param cardNumber
     * @return
     */
    public Migrant findByCardNumberAndIsDel(String cardNumber) {
        return migrantDao.findByCardNumberAndIsDel(cardNumber, IsDel.NOT_DEL);
    }

    /**
     * 找到身份证号码是小区通过和未删除的人
     * @param cardNumber
     * @param communityId
     * @return
     */
    public Migrant findByCardNumberAndCommunityIdAndIsDelAndStatus(String cardNumber, Long communityId) {
        return migrantDao.findByCardNumberAndCommunityIdAndIsDelAndStatus(cardNumber, communityId, IsDel.NOT_DEL, UserStatus.PASS);
    }

    /**
     * 查看这个社区所有通过的人
     * @param communityId
     * @return
     */
    public List<Migrant> findByCommunityIdAndIsDelAndStatus(Long communityId)
    {
        return migrantDao.findByCommunityIdAndIsDelAndStatus(communityId,IsDel.NOT_DEL, UserStatus.PASS);
    }

    /**
     * 查看所有有效的信息
     * @return
     */
    public List<Migrant> findByIsDelAndStatus(){
        return migrantDao.findByIsDelAndStatus(IsDel.NOT_DEL, UserStatus.PASS);
    }

    /**
     * 根据社区查询检测的
     * @param communityId
     * @return
     */
    public List<Migrant> findHasTest(Long communityId, List<String> cardNumbers){

        Specification<Migrant> specification = new Specification<Migrant>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Migrant> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate cardNumber = criteriaBuilder.in(root.get("cardNumber")).value(cardNumbers);
                Predicate status = criteriaBuilder.equal(root.get("status"), UserStatus.PASS);
                cardNumber = criteriaBuilder.and(cardNumber,status);
                Predicate isDel = criteriaBuilder.equal(root.get("isDel"), IsDel.NOT_DEL);
                cardNumber = criteriaBuilder.and(cardNumber,isDel);
                if(communityId != 0){
                    Predicate predicate = criteriaBuilder.equal(root.get("community"), communityId);
                    cardNumber = criteriaBuilder.and(cardNumber,predicate);
                }
                return cardNumber;
            }
        };
        List<Migrant> findAll = migrantDao.findAll(specification);
        return findAll;
    }


    /**
     * 查询自己的通过的流动信息
     * @param cardNumber
     * @return
     */
    public Migrant findByCardNumberAndIsDelAndStatus(String cardNumber)
    {
        return migrantDao.findByCardNumberAndIsDelAndStatus(cardNumber, IsDel.NOT_DEL, UserStatus.PASS);
    }

}
