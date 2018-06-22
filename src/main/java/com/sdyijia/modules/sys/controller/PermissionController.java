package com.sdyijia.modules.sys.controller;

import com.sdyijia.modules.sys.bean.SysPermission;
import com.sdyijia.modules.sys.repository.SysPermissionRepository;
import com.sdyijia.modules.sys.service.PermissionService;
import com.sdyijia.utils.page.PageableTools;
import com.sdyijia.utils.tool.ToolStr;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PermissionController extends SysController {

    /**
     * 对应pojo的名字
     */
    public static final String POJO_NAME = "/permission";
    /**
     * 权限url前缀
     */
    public static final String PERMISSION_URL_PREFIX = PERMISSION_PREFIX + POJO_NAME;
    /**
     * 列表
     */
    public static final String LIST_URL = PERMISSION_URL_PREFIX + "/list";
    /**
     * 跳转
     */
    public static final String MODIFY_URL = PERMISSION_URL_PREFIX + "/modify";
    /**
     * 保存
     */
    public static final String SAVE_URL = PERMISSION_URL_PREFIX + "/save";
    /**
     * 删除
     */
    public static final String REMOVE_URL = PERMISSION_URL_PREFIX + "/remove";

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(LIST_URL)
    @RequiresPermissions(LIST_URL)
    public String list(Model m, Integer page, Long parentid, String name) {
        List<SysPermission> parlist = new ArrayList<>();
        if (parentid != null || !ToolStr.isEmpty(name)) {
            parlist = sysPermissionRepository.findAllByParentIdAndNameOrderByIdAsc(parentid, name, PageableTools.basicPage(page));
        }
        m.addAttribute("list", parlist);
        m.addAttribute("name", name);
        m.addAttribute("parentid", parentid);
        return LIST_URL;
    }


}
