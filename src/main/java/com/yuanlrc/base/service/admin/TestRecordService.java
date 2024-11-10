package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.dao.admin.TestRecordDao;
import com.yuanlrc.base.entity.admin.TestRecord;
import com.yuanlrc.base.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 核酸检测Service
 */
@Service
public class TestRecordService {

    @Autowired
    private TestRecordDao testRecordDao;

    /**
     * 保存
     * @param testRecord
     * @return
     */
    public TestRecord save(TestRecord testRecord){
        return testRecordDao.save(testRecord);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        testRecordDao.deleteById(id);
    }

    /**
     * 根据身份证号查询
     * @param cardNumber
     * @return
     */
    public List<TestRecord> findByCardNumber(String cardNumber){
        return testRecordDao.findByCardNumberOrderByCreateTimeDesc(cardNumber);
    }


    public TestRecord find(Long id)
    {
        return testRecordDao.find(id);
    }

    /**
     * 根据社区分页查询
     * @param pageBean
     * @param testRecord
     * @param communityId
     * @return
     */
    public PageBean<TestRecord> findList(PageBean<TestRecord> pageBean, TestRecord testRecord,Long communityId){

        Specification<TestRecord> specification = new Specification<TestRecord>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate community = criteriaBuilder.equal(root.get("community"), communityId);
                Predicate predicate = criteriaBuilder.notEqual(root.get("status"), UserStatus.NOT_PASS);
                community = criteriaBuilder.and(community,predicate);
                if(!StringUtils.isEmpty(testRecord.getCardNumber())){
                    Predicate cardNumber = criteriaBuilder.equal(root.get("cardNumber"), testRecord.getCardNumber());
                    community = criteriaBuilder.and(community,cardNumber);
                }
                return community;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "status");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<TestRecord> findAll = testRecordDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    public int countByCardNumberAndCommunityIdAndGatherTimeGreaterThanEqual(String cardNumber, Long communityId, String gatherTime)
    {
        return testRecordDao.countByCardNumberAndCommunityIdAndGatherTimeGreaterThanEqualAndStatus(cardNumber, communityId,DateUtil.toDate(gatherTime), UserStatus.PASS);
    }


    /**
     * 根据开始时间和社区查询做过核酸记录的身份证集合
     * @param gatherTime
     * @return
     */
    public List<String> findHasTest(String gatherTime,Long communityId){
        
        Specification<TestRecord> specification = new Specification<TestRecord>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TestRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("status"), UserStatus.PASS);
                if(!StringUtils.isEmpty(gatherTime)){
                    Predicate greater = criteriaBuilder.greaterThanOrEqualTo(root.get("gatherTime"), DateUtil.toDate(gatherTime));
                    predicate = criteriaBuilder.and(predicate,greater);
                }
                if(communityId != 0){
                    Predicate community = criteriaBuilder.equal(root.get("community"),communityId);
                    predicate = criteriaBuilder.and(predicate,community);
                }
                return predicate;
            }
        };
        List<TestRecord> findAll = testRecordDao.findAll(specification);
        List<String> cardNumbers = findAll.stream().map(TestRecord -> TestRecord.getCardNumber()).collect(Collectors.toList());
        return cardNumbers;
    }

    public TestRecord save2(TestRecord testRecord) {
        return testRecordDao.save(testRecord);
    }
}
