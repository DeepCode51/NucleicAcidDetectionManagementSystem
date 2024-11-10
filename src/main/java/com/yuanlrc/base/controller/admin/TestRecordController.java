package com.yuanlrc.base.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.MigrantService;
import com.yuanlrc.base.service.admin.ResidentService;
import com.yuanlrc.base.service.admin.TestRecordService;
import com.yuanlrc.base.util.ExportExcelUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 核酸记录Controller
 */
@Controller
@RequestMapping("/admin/testRecord")
public class TestRecordController {

    @Autowired
    private TestRecordService testRecordService;

    @Autowired
    private MigrantService migrantService;

    @Autowired
    private ResidentService residentService;

    /**
     * 分页查询
     * @return
     */
    @RequestMapping("/list")
    public String findList(Model model, PageBean<TestRecord> pageBean,TestRecord testRecord){
        User loginedUser = SessionUtil.getLoginedUser();
        model.addAttribute("title","核酸记录");
        model.addAttribute("pageBean",testRecordService.findList(pageBean,testRecord,loginedUser.getCommunity().getId()));
        model.addAttribute("cardNumber",testRecord.getCardNumber());
        model.addAttribute("sexList", Sex.values());
        return "admin/testRecord/list";
    }

    /**
     * 添加页面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("title","添加核酸记录");

        model.addAttribute("sexList",Sex.values());
        model.addAttribute("male", Sex.MALE);
        return "admin/testRecord/add";
    }

    /**
     * 添加
     * @param testRecord
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(TestRecord testRecord){

        User user = SessionUtil.getLoginedUser();
        if(user == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }

        if(user.getCommunity() == null){
            return Result.error(CodeMsg.ADMIN_COMMUNITY_NULL_ERROR);
        }

        CodeMsg validate = ValidateEntityUtil.validate(testRecord);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(testRecord.getGatherTime() == null){
            return Result.error(CodeMsg.DATE_NULL_ERROR);
        }

        if(StringUtils.isEmpty(testRecord.getImg())){
            return Result.error(CodeMsg.ADMIN_IMG_NULL_ERROR);
        }

        //手机号码格式验证
        if(!StringUtil.isMobile(testRecord.getGatherMobile()))
            return Result.error(CodeMsg.ADMIN_PHONE_FORMAET_ERROR);

        if(testRecord.getGatherTime().getTime()>new Date().getTime()){
            return Result.error(CodeMsg.ADMIN_DATE_ERROR);
        }

        testRecord.setCommunity(user.getCommunity());

        if (testRecordService.save(testRecord) == null) {
            return Result.error(CodeMsg.ADMIN_TESTRECORD_SAVE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 审批
     * @param id
     * @param flag
     * @param notPassReason
     * @return
     */
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> approval(Long id, Boolean flag, String notPassReason)
    {
        TestRecord testRecord = testRecordService.find(id);
        if(testRecord == null)
            return Result.error(CodeMsg.DATA_ERROR);

        if(testRecord.getStatus().getCode() != UserStatus.AUDIT.getCode())
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        if(flag)
        {
            testRecord.setStatus(UserStatus.PASS);
        }
        else
        {
            if(notPassReason != null)
            {
                if(notPassReason.trim().length() > 50)
                    return Result.error(CodeMsg.ADMIN_APPROVAL_MSG_LENGTH_ERROR);
            }
            testRecord.setStatus(UserStatus.NOT_PASS);
            testRecord.setReasons(notPassReason);
        }

        if(testRecordService.save2(testRecord) == null)
            return Result.error(CodeMsg.ADMIN_APPROVAL_SAVE_ERROR);

        return Result.success(true);
    }

    /**
     * 导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, String gatherTime)throws Exception
    {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        //设置要导出的文件的名字
        String fileName = "testRecordInfo" + ".xlsx";
        exportExcelUtil.setFileName(fileName);
        //headers表示excel表中第一行的表头
        exportExcelUtil.setHeaders(new String[]{"姓名","性别","地址","身份证号码"});
        exportExcelUtil.setSheetName("未核酸人员信息");
        //创建表格
        HSSFSheet sheet = exportExcelUtil.create();

        User loginedUser = SessionUtil.getLoginedUser();

        //查出该社区信息
        List<Migrant> migrants = migrantService.findByCommunityIdAndIsDelAndStatus(loginedUser.getCommunity().getId());

        //查出该社区的常驻人口
        List<Resident> residents = residentService.findByCommunityIdAndIsDel(loginedUser.getCommunity().getId(), IsDel.NOT_DEL);

        List<TestRecordInfo> testRecordInfos = new ArrayList<>();

        migrants.forEach( migrant ->
        {
            Integer count = testRecordService.countByCardNumberAndCommunityIdAndGatherTimeGreaterThanEqual(migrant.getCardNumber(), loginedUser.getCommunity().getId(), gatherTime);
            if(count == 0) {
                TestRecordInfo testRecordInfo = new TestRecordInfo
                        (migrant.getName(), Sex.getValueByCode(migrant.getSex()), migrant.getAddress(), migrant.getCardNumber());
                testRecordInfos.add(testRecordInfo);
            }
        });

        residents.forEach( resident ->
        {
            int count = testRecordService.countByCardNumberAndCommunityIdAndGatherTimeGreaterThanEqual(resident.getCardNumber(), loginedUser.getCommunity().getId(), gatherTime);
            if(count == 0) {
                TestRecordInfo testRecordInfo = new TestRecordInfo
                        (resident.getName(), Sex.getValueByCode(resident.getSex()), resident.getAddress(), resident.getCardNumber());
                testRecordInfos.add(testRecordInfo);
            }
        });

        int rowNum = 1;
        //在表中存放查询到的数据放入对应的列
        for (TestRecordInfo item : testRecordInfos) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getName());
            row1.createCell(1).setCellValue(item.getSex());
            row1.createCell(2).setCellValue(item.getAddress());
            row1.createCell(3).setCellValue(item.getCardNumber());
            rowNum++;
        }

        exportExcelUtil.export(response);
    }

    /**
     * 根据id查看详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String show(Long id,Model model){
        model.addAttribute("testRecord",testRecordService.find(id));
        model.addAttribute("male", Sex.MALE);
        return "admin/testRecord/show";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id){
        try{
            testRecordService.delete(id);
        }catch (Exception e){
            Result.error(CodeMsg.TESET_RECORD_DELETE_ERROR);
        }
        return Result.success(true);
    }
}