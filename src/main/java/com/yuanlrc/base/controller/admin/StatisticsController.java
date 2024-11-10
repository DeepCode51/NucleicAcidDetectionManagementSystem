package com.yuanlrc.base.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yuanlrc.base.bean.IsDel;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.bean.UserType;
import com.yuanlrc.base.entity.admin.Migrant;
import com.yuanlrc.base.entity.admin.Resident;
import com.yuanlrc.base.entity.admin.TestRecord;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.service.admin.CommunityService;
import com.yuanlrc.base.service.admin.MigrantService;
import com.yuanlrc.base.service.admin.ResidentService;
import com.yuanlrc.base.service.admin.TestRecordService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计显示Controller
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private TestRecordService testRecordService;

    @Autowired
    private MigrantService migrantService;

    @Autowired
    private CommunityService communityService;

    /**
     * 统计页面
     * @return
     */
    @RequestMapping(value = "/statistics")
    public String statistics(Model model){
        model.addAttribute("title","核酸记录统计");
        model.addAttribute("superAdmin",UserType.SUPER_ADMIN.getCode());
        model.addAttribute("communities",communityService.findAll());
        return "admin/statistics/statistics";
    }

    @RequestMapping(value = "/statistics",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> statisticsMap(@RequestParam("gatherTime") String gatherTime,@RequestParam(value = "communityId",defaultValue = "0") Long communityId){
        User loginedUser = SessionUtil.getLoginedUser();

        if(loginedUser.getRole().getId().longValue() == UserType.ADMIN.getCode().longValue()){
            communityId = loginedUser.getCommunity().getId();
        }
        //总人数
        Integer sumNumber = 0;
        if(communityId.longValue() != 0){
            List<Resident> residents = residentService.findByCommunityIdAndIsDel(communityId, IsDel.NOT_DEL);
            List<Migrant> migrants = migrantService.findByCommunityIdAndIsDelAndStatus(communityId);
            sumNumber = residents.size() + migrants.size();
        }else{
            //如果是0表示查询全部社区
            List<String> cardNumberList = new ArrayList<>();;
            List<Resident> residents = residentService.findByIsDel(IsDel.NOT_DEL);
            List<String> residentCardNumbers = residents.stream().map(Resident -> Resident.getCardNumber()).collect(Collectors.toList());

            List<Migrant> migrants = migrantService.findByIsDelAndStatus();
            List<String> migrantsCardNumbers = migrants.stream().map(Migrant -> Migrant.getCardNumber()).collect(Collectors.toList());

            //对同一身份证去重
            cardNumberList.addAll(residentCardNumbers);
            cardNumberList.addAll(migrantsCardNumbers);
            cardNumberList = cardNumberList.stream().distinct().collect(Collectors.toList());

            sumNumber = cardNumberList.size();
        }

        List<String> cardNumbers = testRecordService.findHasTest(gatherTime,communityId);
        //已做核酸人员数
        Integer hasTest = 0;
        if(cardNumbers.size() > 0){
            List<String> cardNumberList = new ArrayList<>();;
            //常驻人员表已做核酸列表
            List<Resident> residents = residentService.findHasTest(communityId, cardNumbers);
            List<String> residentCardNumbers = residents.stream().map(Resident -> Resident.getCardNumber()).collect(Collectors.toList());

            //流动人员表已做核酸列表
            List<Migrant> migrants = migrantService.findHasTest(communityId, cardNumbers);
            List<String> migrantsCardNumbers = migrants.stream().map(Migrant -> Migrant.getCardNumber()).collect(Collectors.toList());

            //对同一身份证去重
            cardNumberList.addAll(residentCardNumbers);
            cardNumberList.addAll(migrantsCardNumbers);
            cardNumberList = cardNumberList.stream().distinct().collect(Collectors.toList());

            hasTest = cardNumberList.size();
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("已做核酸人数",hasTest);
        map.put("未做核酸人数",sumNumber - hasTest);

        return map;
    }

}
