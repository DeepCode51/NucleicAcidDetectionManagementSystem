package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Community;
import com.yuanlrc.base.service.admin.CommunityService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 地点
 */
@Controller
@RequestMapping("/admin/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private OperaterLogService operaterLogService;


    /**
     * 社区列表
     * @param community
     * @param pageBean
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Community community, PageBean<Community> pageBean, Model model)
    {
        model.addAttribute("pageBean", communityService.findList(community, pageBean));
        model.addAttribute("title", "地址管理");
        model.addAttribute("name", community.getName());
        return "admin/community/list";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            communityService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_COMMUNITY_DELETE_ERROR);
        }

        operaterLogService.add("地址删除成功:" + id);
        return Result.success(true);
    }


    @GetMapping("/add")
    public String add(Model model)
    {
        return "admin/community/add";
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id)
    {
        model.addAttribute("community", communityService.find(id));
        return "admin/community/edit";
    }

    /**
     * 添加地址
     * @param community
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(Community community)
    {

        //实体类验证
        CodeMsg codeMsg = ValidateEntityUtil.validate(community);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(communityService.isExist(community,0l)){
            return Result.error(CodeMsg.ADMIN_COMMUNITY_EXIST_ERROR);
        }

        if(communityService.save(community) == null)
            return Result.error(CodeMsg.ADMIN_COMMUNITY_ADD_ERROR);

        return Result.success(true);
    }

    /**
     * 编辑地址
     * @param community
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result<Boolean> edit(Community community)
    {
        Community find = communityService.find(community.getId());

        find.setName(community.getName());
        find.setStreet(community.getStreet());

        //实体类验证
        CodeMsg codeMsg = ValidateEntityUtil.validate(find);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(communityService.isExist(find,find.getId())){
            return Result.error(CodeMsg.ADMIN_COMMUNITY_EXIST_ERROR);
        }

        if(communityService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_COMMUNITY_EDIT_ERROR);

        return Result.success(true);
    }
}
