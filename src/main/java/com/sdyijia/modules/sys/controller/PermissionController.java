package com.sdyijia.modules.sys.controller;

import com.sdyijia.exception.SaveException;
import com.sdyijia.modules.base.BaseController;
import com.sdyijia.modules.sys.bean.SysPermission;
import com.sdyijia.modules.sys.repository.SysPermissionRepository;
import com.sdyijia.modules.sys.service.PermissionService;
import com.sdyijia.utils.tool.ToolStr;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
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
    public static final String UPDATA_URL = PERMISSION_URL_PREFIX + "/updata";
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
    private SysPermissionRepository permissionRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;


    private void addURL(Model m) {
        m.addAttribute("REMOVE_URL", REMOVE_URL);
        m.addAttribute("SAVE_URL", SAVE_URL);
        m.addAttribute("MODIFY_URL", MODIFY_URL);
        m.addAttribute("LIST_URL", LIST_URL);
        m.addAttribute("UPDATA_URL", UPDATA_URL);
    }

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(LIST_URL)
    @RequiresPermissions(LIST_URL)
    public String list(Model m, Integer page, Long parentId, String name) {
        List<SysPermission> parlist = new ArrayList<>();
        parlist = sysPermissionRepository.findAllByParentIdAndNameLikeOrderByIdAsc(parentId, ToolStr.fuzzy(name), null);
        m.addAttribute("functionlist", parlist);
        if (name == null) name = "";
        if (parentId == null) parentId = -1l;
        m.addAttribute("name", name);
        m.addAttribute("parentId", parentId);
        addURL(m);
        return LIST_URL;
    }

    @RequestMapping(MODIFY_URL)
    @RequiresPermissions(MODIFY_URL)
    public String modifyPermission(Model m, Integer page, Long parentId, String ids) {
        if (ids != null && ids != "" && ids.trim().length() > 0) {
            long l = Long.parseLong(ids);
            Optional<SysPermission> byId = permissionRepository.findById(l);
            if (byId.isPresent()) {
                SysPermission sysPermission = byId.get();
                m.addAttribute("perm", sysPermission);
            }
        } else if (parentId != null && parentId != -1l) {
            Optional<SysPermission> byId = permissionRepository.findById(parentId);
            if (byId.isPresent()) {
                SysPermission sysPermission = byId.get();
                m.addAttribute("parentId", sysPermission.getId());
            }
        }
        addURL(m);
        return PERMISSION_URL_PREFIX + "/permadd";
    }

    @RequestMapping(SAVE_URL)
    @RequiresPermissions(SAVE_URL)
    public String save(HttpServletRequest request, @NonNull SysPermission sysPermission) {
        if (sysPermission != null && sysPermission.getId() == null
                && sysPermission.getSort() != null && sysPermission.getResourceType() != null) {
            if (sysPermission.getParent() != null && sysPermission.getParent().getId() != null) {
                SysPermission dbParentPer = permissionRepository.getOne(sysPermission.getParent().getId());
                sysPermission.setLevel(dbParentPer.getLevel() + 1);
                sysPermission.setParent(dbParentPer);
                //更新父权限的childrens字段
                sysPermission = permissionRepository.save(sysPermission);
                List<SysPermission> childrens = new ArrayList<>();
                childrens.addAll(dbParentPer.getChildren());
                childrens.add(sysPermission);
                dbParentPer.setChildren(childrens);
                sysPermissionRepository.saveAndFlush(dbParentPer);
                return BaseController.REDIRECT + LIST_URL + "?parentId=" + sysPermission.getParent().getId();
            } else {
                sysPermission.setParent(null);
                sysPermission.setLevel(0);
                permissionRepository.save(sysPermission);
            }
        } else {
            throw new SaveException("传参错误不要携带id，请携带必要的参数");
        }
        return BaseController.REDIRECT + LIST_URL;
    }

    @RequestMapping(UPDATA_URL)
    @RequiresPermissions(UPDATA_URL)
    public String updata(SysPermission sysPermission) {
        if (sysPermission != null && sysPermission.getId() != null) {
            SysPermission dbPermission = sysPermissionRepository.getOne(sysPermission.getId());
            dbPermission.setSort(sysPermission.getSort());
            dbPermission.setName(sysPermission.getName());
            dbPermission.setResourceType(sysPermission.getResourceType());
            dbPermission.setUrl(sysPermission.getUrl());
            dbPermission.setPermission(sysPermission.getPermission());
            dbPermission.setAvailable(sysPermission.getAvailable());
            dbPermission.setShowLeft(sysPermission.getShowLeft());
            //更新页面传过来的更新
            sysPermissionRepository.save(dbPermission);
            if (dbPermission.getParent() != null) {
                return BaseController.REDIRECT + LIST_URL + "?parentId=" + dbPermission.getParent().getId();
            } else {
                return BaseController.REDIRECT + LIST_URL;
            }
        } else {
            throw new SaveException("传参错误请传入id");
        }
    }

    /**
     * 删除权限分组
     *
     * @param m
     * @param ids
     * @return
     */
    @GetMapping(REMOVE_URL)
    @Transactional
    public String delete(Model m, String ids) {
        Long l = Long.parseLong(ids);
        if (l != null) {
            SysPermission per = permissionRepository.getOne(l);
            permissionRepository.deleteById(l);
            if (per.getParent() != null) {
                return BaseController.REDIRECT + LIST_URL + "?parentId=" + per.getParent().getId();
            }
        }
        return BaseController.REDIRECT + LIST_URL;
    }
}
