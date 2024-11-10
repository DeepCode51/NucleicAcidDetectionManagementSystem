package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.Resident;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.service.admin.CommunityService;
import com.yuanlrc.base.service.admin.ResidentService;
import com.yuanlrc.base.util.ExportExcelUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 常驻人口Controller
 */
@Controller
@RequestMapping("/admin/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;
    @Autowired
    private CommunityService communityService;

    @RequestMapping("/list")
    public String list(Model model, PageBean<Resident> pageBean,Resident resident){
        User loginedUser = SessionUtil.getLoginedUser();
        model.addAttribute("title", "常驻人员列表");
        model.addAttribute("pageBean",residentService.findList(resident,pageBean,loginedUser.getCommunity().getId()));
        model.addAttribute("sexList",Sex.values());
        model.addAttribute("typeList",Type.values());
        model.addAttribute("name",resident.getName());
        return "admin/resident/list";
    }

    /**
     * 添加页面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("title","添加常驻人员");
        model.addAttribute("sexList",Sex.values());
        model.addAttribute("male", Sex.MALE);
        model.addAttribute("typeList",Type.values());
        return "admin/resident/add";
    }

    /**
     * 添加表单提交
     * @param resident
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Resident resident){

        User user = SessionUtil.getLoginedUser();
        if(user == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }

        if(user.getCommunity() == null){
            return Result.error(CodeMsg.ADMIN_COMMUNITY_NULL_ERROR);
        }

        if(!StringUtil.isCard(resident.getCardNumber())){
            return Result.error(CodeMsg.COMMON_IDCARD_FORMAET_ERROR);
        }

        if(String.valueOf(resident.getAccountNumber()).length() != 9){
            return Result.error(CodeMsg.ACCOUNT_NUMBER_LENGTH_ERROR);
        }

        CodeMsg validate = ValidateEntityUtil.validate(resident);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        resident.setCommunity(user.getCommunity());

        if(residentService.save(resident) == null){
            return Result.error(CodeMsg.RESIDENT_SAVE_ERROR);
        }

        return Result.success(true);
    }

    //导入
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> importResident(@RequestParam("file") MultipartFile file)throws Exception{
        HashMap<String, Resident> residentHashMap = new HashMap<>();

        User user = SessionUtil.getLoginedUser();
        if(user == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        if(user.getCommunity() == null){
            return Result.error(CodeMsg.ADMIN_COMMUNITY_NULL_ERROR);
        }

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        InputStream ins = file.getInputStream();
        //判断文件格式
        if(!suffix.equals("xlsx")&&!suffix.equals("xls")){
            return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_ADD_ERROR);
        }
        Workbook workbook = WorkbookFactory.create(ins);
        //获取Excel
        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null) {
            return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_ADD_ERROR);
        }

        //accountNumber address type name sex cardNumber relation
        String[] strings = new String[7];

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Resident resident = new Resident();
            Row row = sheet.getRow(i);
            //如果没有这一行跳过
            if(row == null){
                continue;
            }
            try{
                for (int len = 0; len < strings.length; len++) {
                    row.getCell(len).setCellType(CellType.STRING);
                    strings[len] = row.getCell(len).getStringCellValue();
                }
            }catch (Exception e){
                return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_EXCEL_ERROR);
            }

            for (int l = 0; l < strings.length; l++) {
                if(StringUtils.isEmpty(strings[l])){
                    return Result.error(CodeMsg.ADMIN_IMPORT_EXCEL_NULL_ERROR);
                }
            }

            if(!StringUtil.isCard(strings[5])){
                return Result.error(CodeMsg.COMMON_IDCARD_FORMAET_ERROR2);
            }

            Resident byCardNumber = residentHashMap.get(strings[5]);
            if(byCardNumber != null){
                return Result.error(CodeMsg.ADMIN_EXCEL_IDCARD_REPEATED);
            }

            Type type = Type.getType(strings[2]);
            if(type == null){
                return Result.error(CodeMsg.ADMIN_TYPE_FORMAT_ERROR);
            }
            Sex sex = Sex.getSex(strings[4]);
            if(sex == null){
                return Result.error(CodeMsg.ADMIN_SEX_FORMAT_ERROR);
            }
            if(strings[0].length() != 9){
                return Result.error(CodeMsg.ACCOUNT_NUMBER_LENGTH_ERROR);
            }

            resident.setAccountNumber(Integer.parseInt(strings[0]));
            resident.setAddress(strings[1]);
            resident.setType(type);
            resident.setName(strings[3]);
            resident.setSex(sex.getCode());
            resident.setCardNumber(strings[5]);
            resident.setRelation(strings[6]);
            resident.setCommunity(user.getCommunity());

            CodeMsg validate = ValidateEntityUtil.validate(resident);
            if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
                return Result.error(validate);
            }

            residentHashMap.put(strings[5],resident);
        }

        try{
            residentService.save(residentHashMap);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_MIGRANT_LIST_ADD_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 导出
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        //设置要导出的文件的名字
        String fileName = "resident"+".xlsx";
        exportExcelUtil.setFileName(fileName);
        //headers表示excel表中第一行的表头
        exportExcelUtil.setHeaders(new String[]{"户号","本户地址","户口类型","姓名","性别","身份证号码","与户主关系"});
        exportExcelUtil.setSheetName("常驻人员");
        //创建表格
        HSSFSheet sheet = exportExcelUtil.create();

        User loginedUser = SessionUtil.getLoginedUser();
        //查出该社区信息
        List<Resident> residents = residentService.findByCommunityIdAndIsDel(loginedUser.getCommunity().getId(),IsDel.NOT_DEL);

        int rowNum = 1;
        //在表中存放查询到的数据放入对应的列
        for (Resident resident : residents) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(resident.getAccountNumber());
            row.createCell(1).setCellValue(resident.getAddress());
            row.createCell(2).setCellValue(resident.getType().getValue());
            row.createCell(3).setCellValue(resident.getName());
            row.createCell(4).setCellValue(Sex.getValueByCode(resident.getSex()));
            row.createCell(5).setCellValue(resident.getCardNumber());
            row.createCell(6).setCellValue(resident.getRelation());
            rowNum++;
        }

        exportExcelUtil.export(response);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id){
        try {
            residentService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.RESIDENT_DELETE_ERROR);
        }
        return Result.success(true);
    }

}
