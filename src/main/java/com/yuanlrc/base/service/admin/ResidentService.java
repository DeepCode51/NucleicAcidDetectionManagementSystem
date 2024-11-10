package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Sex;
import com.yuanlrc.base.bean.Type;
import com.yuanlrc.base.dao.admin.MigrantDao;
import com.yuanlrc.base.dao.admin.ResidentDao;
import com.yuanlrc.base.entity.admin.Migrant;
import com.yuanlrc.base.entity.admin.Resident;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * 常住人口Service
 */
@Service
public class ResidentService {

    @Autowired
    private ResidentDao residentDao;
    @Autowired
    private MigrantDao migrantDao;

    /**
     * 根据身份证号和社区查询有效信息
     * @param cardNumber
     * @param city
     * @param isdel
     * @return
     */
    public Resident findByCardNumberAndCommunityCityAndIsDel(String cardNumber, String city, IsDel isdel){
        return residentDao.findByCardNumberAndCommunityCityAndIsDel(cardNumber,city,isdel);
    }

    /**
     * 根据身份证号和社区id查询有效信息
     * @param cardNumber
     * @param communityId
     * @param isdel
     * @return
     */
    public Resident findByCardNumberAndCommunityIdAndIsDel(String cardNumber, Long communityId, IsDel isdel){
        return residentDao.findByCardNumberAndCommunityIdAndIsDel(cardNumber,communityId,isdel);
    }

    /**
     * 保存
     * @param resident
     * @return
     */
    @Transactional
    public Resident save(Resident resident){
        Resident byCardNumberAndIsDel = residentDao.findByCardNumberAndIsDel(resident.getCardNumber(), IsDel.NOT_DEL);
        //如果已在常驻表中
        if(byCardNumberAndIsDel != null){
            byCardNumberAndIsDel.setIsDel(IsDel.DEL);
            residentDao.save(byCardNumberAndIsDel);
        }

        Migrant migrant = migrantDao.findByCardNumberAndCommunityCityAndIsDel
                (resident.getCardNumber(), resident.getCommunity().getCity(), IsDel.NOT_DEL);
        //在该社区流动人口中
        if(migrant != null){
            migrant.setIsDel(IsDel.DEL);
            migrantDao.save(migrant);
        }

        return residentDao.save(resident);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Resident find(Long id){
        return residentDao.find(id);
    }

    /**
     * 根据社区分页查询
     * @param resident
     * @param pageBean
     * @param communityId
     * @return
     */
    public PageBean<Resident> findList(Resident resident,PageBean<Resident> pageBean, Long communityId)
    {
        Specification<Resident> specification = new Specification<Resident>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Resident> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate community = criteriaBuilder.equal(root.get("community"), communityId);

                Predicate predicate = criteriaBuilder.like(root.get("name"), resident.getName() == null ? "%%" : "%"+resident.getName()+"%");

                predicate = criteriaBuilder.and(predicate,community);

                Predicate isDel = criteriaBuilder.equal(root.get("isDel"), IsDel.NOT_DEL);
                predicate = criteriaBuilder.and(predicate, isDel);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Resident> findAll = residentDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        residentDao.deleteById(id);
    }

    /**
     * 找到是否在常驻人口里面
     * @param cardNumber
     * @param
     * @return
     */
    public Resident findByCardNumberAndIsDel(String cardNumber)
    {
        return residentDao.findByCardNumberAndIsDel(cardNumber, IsDel.NOT_DEL);
    }


    /**
     * 根据社区查询
     * @param communityId
     * @param isdel
     * @return
     */
    public List<Resident> findByCommunityIdAndIsDel(Long communityId,IsDel isdel){
        return residentDao.findByCommunityIdAndIsDel(communityId, isdel);
    }

    /**
     * 查询所有有效的信息
     * @param isdel
     * @return
     */
    public List<Resident> findByIsDel(IsDel isdel){
        return  residentDao.findByIsDel(isdel);
    }

    public List<Resident> findNotTest(Long communityId, IsDel isdel,List<String> cardNumber){
        return residentDao.findByCommunityIdAndIsDelAndCardNumberNotIn(communityId,isdel,cardNumber);
    }

    /**
     * 保存导入的数据
     * @param residentHashMap
     */
    @Transactional
    public void save(HashMap<String, Resident> residentHashMap) {
        for (String key : residentHashMap.keySet()) {
            save(residentHashMap.get(key));
        }
    }

    /**
     * 根据社区查询检测的
     * @param communityId
     * @return
     */
    public List<Resident> findHasTest(Long communityId,List<String> cardNumbers){

        Specification<Resident> specification = new Specification<Resident>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Resident> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate cardNumber = criteriaBuilder.in(root.get("cardNumber")).value(cardNumbers);
                Predicate isDel = criteriaBuilder.equal(root.get("isDel"), IsDel.NOT_DEL);
                cardNumber = criteriaBuilder.and(cardNumber,isDel);
                if(communityId != 0){
                    Predicate predicate = criteriaBuilder.equal(root.get("community"), communityId);
                    cardNumber = criteriaBuilder.and(cardNumber,predicate);
                }
                return cardNumber;
            }
        };
        List<Resident> findAll = residentDao.findAll(specification);

        return findAll;
    }

}
