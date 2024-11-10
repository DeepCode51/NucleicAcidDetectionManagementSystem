package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.Migrant;
import com.yuanlrc.base.entity.admin.Resident;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.service.admin.MigrantService;
import com.yuanlrc.base.service.admin.ResidentService;
import com.yuanlrc.base.util.ExportExcelUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 流动人口控制器
 */
@Controller
@RequestMapping("/admin/migrant")
public class MigrantController {

    @Autowired
    private MigrantService migrantService;
    @Autowired
    private ResidentService residentService;

    @GetMapping("/list")
    public String list(Model model, Migrant migrant, PageBean<Migrant> pageBean)
    {
        User loginedUser = SessionUtil.getLoginedUser();
        model.addAttribute("title", "流动人员管理");
        model.addAttribute("pageBean", migrantService.findList(migrant, pageBean,loginedUser.getCommunity().getId()));
        model.addAttribute("name", migrant.getName());
        model.addAttribute("userStatus", UserStatus.values());
        model.addAttribute("sexList", Sex.values());
        return "admin/migrant/list";
    }

    @GetMapping("/add")
    public String add(Model model)
    {
        model.addAttribute("title","添加流动人员");
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("male", Sex.MALE);
        return "admin/migrant/add";
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id)
    {
        return "admin/migrant/edit";
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            migrantService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_MIGRANT_DELETE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 添加流动人口 管理员
     * @param migrant
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(Migrant migrant)
    {
        User user = SessionUtil.getLoginedUser();
        if(user == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        if(user.getCommunity() == null)
            return Result.error(CodeMsg.ADMIN_COMMUNITY_NULL_ERROR);

        migrant.setCommunity(user.getCommunity());



        //身份证号码验证
        if(!StringUtil.isCard(migrant.getCardNumber()))
            return Result.error(CodeMsg.COMMON_IDCARD_FORMAET_ERROR);

        CodeMsg codeMsg = ValidateEntityUtil.validate(migrant);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        //判断是否已经是这个社区常驻人员
        Resident resident = residentService.findByCardNumberAndCommunityCityAndIsDel
                (migrant.getCardNumber(), migrant.getCommunity().getCity(), IsDel.NOT_DEL);
        if(resident != null)
            return Result.error(CodeMsg.ADMIN_HAS_RESIDENT_ERROR);


        if(migrantService.save(migrant) == null)
            return Result.error(CodeMsg.ADMIN_MIGRANT_ADD_ERROR);


        return Result.success(true);
    }

    @PostMapping("/approval")
    @ResponseBody
    public Result<Boolean> approval(Long id, Boolean flag, String notPassReason)
    {
        Migrant migrant = migrantService.find(id);
        if(migrant == null)
            return Result.error(CodeMsg.DATA_ERROR);

        if(migrant.getStatus().getCode() != UserStatus.AUDIT.getCode())
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        if(flag)
        {
            migrant.setStatus(UserStatus.PASS);
        }
        else
        {
            if(notPassReason != null)
            {
                if(notPassReason.trim().length() > 50)
                    return Result.error(CodeMsg.ADMIN_APPROVAL_MSG_LENGTH_ERROR);
            }
            migrant.setReasons(notPassReason);
            migrant.setStatus(UserStatus.NOT_PASS);
        }

        if(migrantService.save2(migrant) == null)
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        return Result.success(true);
    }

    /**
     * 导入
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    @ResponseBody
    public Result<Boolean> importMigrantMsg(@RequestParam("file") MultipartFile file)throws Exception {
        HashMap<String, Migrant> migrantHashMap = new HashMap<>();

        User login = SessionUtil.getLoginedUser();
        if(login == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        if(login.getCommunity() == null)
            return Result.error(CodeMsg.ADMIN_COMMUNITY_NULL_ERROR);

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream ins = file.getInputStream();

        //判断是否是xlsx文件或xls
        if (!suffix.equals("xlsx")&&!suffix.equals("xls")){
            return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_ADD_ERROR);
        }
       Workbook workbook = WorkbookFactory.create(ins);;
        //获取Excel
        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null)
            return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_ADD_ERROR);

        String name = "";
        String sex = "";
        String address = "";
        String cardNumber = "";
        String censusAdress = "";

        for (int line = 1;line<=sheet.getLastRowNum();line++){
            Migrant migrant = new Migrant();
            Row row = sheet.getRow(line);

            //如果没有这一行跳过
            if(row == null)
                continue;

            try
            {
                row.getCell(0).setCellType(CellType.STRING);
                name = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(CellType.STRING);
                sex = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(CellType.STRING);
                address = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(CellType.STRING);
                cardNumber = row.getCell(3).getStringCellValue();
                row.getCell(4).setCellType(CellType.STRING);
                censusAdress = row.getCell(4).getStringCellValue();

            }catch (Exception e)
            {
                return Result.error(CodeMsg.ADMIN_MIGRANT_IMPORT_EXCEL_ERROR);
            }

            if(StringUtils.isEmpty(name) || StringUtils.isEmpty(sex)
                    || StringUtils.isEmpty(address) || StringUtils.isEmpty(cardNumber)
                    || StringUtils.isEmpty(censusAdress))
                return Result.error(CodeMsg.ADMIN_IMPORT_EXCEL_NULL_ERROR);

            //身份证号码验证
            if(!StringUtil.isCard(cardNumber))
                return Result.error(CodeMsg.COMMON_IDCARD_FORMAET_ERROR2);

            //判断身份证号码是否重复了
            Migrant findById = migrantHashMap.get(cardNumber);
            if(findById != null)
                return Result.error(CodeMsg.ADMIN_EXCEL_IDCARD_REPEATED);

            Sex sex1 = Sex.getSex(sex);
            if(sex1 == null){
                return Result.error(CodeMsg.ADMIN_SEX_FORMAT_ERROR);
            }

            migrant.setName(name);
            migrant.setSex(sex1.getCode());
            migrant.setAddress(address);
            migrant.setCardNumber(cardNumber);
            migrant.setCensusAddress(censusAdress);
            migrant.setCommunity(login.getCommunity());


            //判断数据库里面是否存在这个身份证号码
            Resident resident = residentService.findByCardNumberAndCommunityCityAndIsDel
                    (migrant.getCardNumber(), migrant.getCommunity().getCity(), IsDel.NOT_DEL);
            if(resident != null) {
                CodeMsg codeMsg = CodeMsg.ADMIN_HAS_RESIDENT_ERROR;
                codeMsg.setMsg(codeMsg.getMsg() + ",Excel第" + line + "行");
                return Result.error(codeMsg);
            }

            migrantHashMap.put(cardNumber, migrant);
        }

        try
        {
            migrantService.save(migrantHashMap);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_MIGRANT_LIST_ADD_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        //设置要导出的文件的名字
        String fileName = "migrants"+".xlsx";
        exportExcelUtil.setFileName(fileName);
        //headers表示excel表中第一行的表头
        exportExcelUtil.setHeaders(new String[]{"姓名","性别","地址","身份证号码","户籍"});
        exportExcelUtil.setSheetName("流动人员");
        //创建表格
        HSSFSheet sheet = exportExcelUtil.create();

        User loginedUser = SessionUtil.getLoginedUser();
        //查出该社区信息
        List<Migrant> migrants = migrantService.findByCommunityIdAndIsDels(loginedUser.getCommunity().getId(),IsDel.NOT_DEL);

        int rowNum = 1;
        //在表中存放查询到的数据放入对应的列
        for (Migrant item : migrants) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getName());
            row1.createCell(1).setCellValue(Sex.getValueByCode(item.getSex()));
            row1.createCell(2).setCellValue(item.getAddress());
            row1.createCell(3).setCellValue(item.getCardNumber());
            row1.createCell(4).setCellValue(item.getCensusAddress());
            rowNum++;
        }

        exportExcelUtil.export(response);
    }
}
