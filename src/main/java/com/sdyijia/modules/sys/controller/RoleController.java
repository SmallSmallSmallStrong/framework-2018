package com.sdyijia.modules.sys.controller;

import com.sdyijia.modules.base.BaseController;
import com.sdyijia.modules.sys.bean.SysRole;
import com.sdyijia.modules.sys.repository.RoleRepository;
import com.sdyijia.utils.SzUtil;
import com.sdyijia.utils.page.PageableTools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RoleController {
    /**
     * 对应pojo的名字
     */
    public static final String POJO_NAME = "role";
    /**
     * 权限url前缀
     */
    public static final String PERMISSION_URL_PREFIX = "/sys/" + POJO_NAME;

    /**
     * 列表
     */
    public static final String LIST_URL = PERMISSION_URL_PREFIX + "/rolelist";
    /**
     * 跳转
     */
    public static final String MODIFY_URL = PERMISSION_URL_PREFIX + "/modify";
    public static final String UP_URL = PERMISSION_URL_PREFIX + "/updata";
    /**
     * 保存
     */
    public static final String SAVE_URL = PERMISSION_URL_PREFIX + "/save";
    /**
     * 删除
     */
    public static final String REMOVE_URL = PERMISSION_URL_PREFIX + "/remove";
    /**
     * 设置权限跳转
     */
    public static final String MODIFY_ROLE_PERMISSION_URL = PERMISSION_URL_PREFIX + "/modifyRolePermission";
    /**
     * 设置权限
     */
    public static final String SAVE_ROLE_PERMISSION_URL = PERMISSION_URL_PREFIX + "/saveRolePermission";

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(MODIFY_URL)
    @RequiresPermissions(MODIFY_URL)
    public String modify(Model m, Long id) {
        if (id == null) {
            return PERMISSION_URL_PREFIX + "/roleadd";
        } else {
            m.addAttribute("dbrole", roleRepository.getOne(id));
            return PERMISSION_URL_PREFIX + "/roleupdata";
        }
    }

    /**
     * 返回所有的角色
     *
     * @param m
     * @param name name
     * @param page 第几页
     * @return
     */
    @RequestMapping(LIST_URL)
    @RequiresPermissions(LIST_URL)
    public String list(Model m, @ModelAttribute("name") String name, Integer page) {
        PageableTools.basicPage(page);
        List<SysRole> list = roleRepository.findAllByNameLike(name);
        m.addAttribute("rolelist", list);
        return LIST_URL;
    }

    @PostMapping(UP_URL)
    @RequiresPermissions(UP_URL)
    public String up(Model m, SysRole role) {
        if (role != null && role.getId() != null) {
            SysRole dbrole = roleRepository.getOne(role.getId());
            if (SzUtil.nonNull(dbrole, SysRole.class, "id", "permissions", "userInfos")) {
                dbrole.setAvailable(role.getAvailable());
                dbrole.setRemark(role.getRemark());
                dbrole.setName(role.getName());
            }
        }
        return BaseController.REDIRECT + LIST_URL;
    }


}
