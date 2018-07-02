package com.sdyijia.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sdyijia.modules.base.BaseController;
import com.sdyijia.modules.sys.bean.SysPermission;
import com.sdyijia.modules.sys.bean.SysRole;
import com.sdyijia.modules.sys.repository.RoleRepository;
import com.sdyijia.modules.sys.repository.SysPermissionRepository;
import com.sdyijia.utils.SzUtil;
import com.sdyijia.utils.page.PageableTools;
import com.sdyijia.utils.tool.ToolStr;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

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
     * 设置权限
     */
    public static final String SAVE_ROLE_PERMISSION_URL = PERMISSION_URL_PREFIX + "/saveRolePermission";

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    private void addURL(Model m) {
        m.addAttribute("REMOVE_URL", REMOVE_URL);
        m.addAttribute("SAVE_URL", SAVE_URL);
        m.addAttribute("MODIFY_URL", MODIFY_URL);
        m.addAttribute("LIST_URL", LIST_URL);
        m.addAttribute("UP_URL", UP_URL);
        m.addAttribute("SAVE_ROLE_PERMISSION_URL", SAVE_ROLE_PERMISSION_URL);
    }

    @RequestMapping(MODIFY_URL)
    @RequiresPermissions(MODIFY_URL)
    public String modify(Model m, Long id) {
        addURL(m);
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
        List<SysRole> list = roleRepository.findAllByNameLike(ToolStr.fuzzy(name));
        m.addAttribute("rolelist", list);
        addURL(m);
        return LIST_URL;
    }

    @GetMapping(UP_URL)
    @RequiresPermissions(UP_URL)
    public String toup(Model m, Long id) {
        SysRole role = roleRepository.getOne(id);
        m.addAttribute("dbrole", role);
        return "sys/role/roleupdata";
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
                dbrole.setUpdataTime(new Date());
                roleRepository.save(dbrole);
            }
        }
        addURL(m);
        return BaseController.REDIRECT + LIST_URL;
    }


    /**
     * 通过角色id查询该角色的权限列表
     *
     * @return
     */
    @GetMapping(SAVE_ROLE_PERMISSION_URL)
    @RequiresPermissions(SAVE_ROLE_PERMISSION_URL)
    public ModelAndView permission(Long id) {
        SysRole role = roleRepository.getOne(id);
        ModelAndView mv = new ModelAndView("sys/role/rolepremission");
        if (role == null) {
            mv.setViewName(BaseController.REDIRECT + LIST_URL);
            return mv;
        }
        List<SysPermission> rolePremissionList = role.getPermissions();
        //获取所有可用的权限
        List<SysPermission> perlist = sysPermissionRepository.findAllByAvailableTrue();
        if (Objects.nonNull(perlist) && perlist.size() > 0) {
            rolePremissionList.sort(Comparator.comparing(p -> p.getId()));
            JSONArray json = new JSONArray();
            perlist.forEach(premission -> {
                Map<String, Object> map = new HashMap<>();
                map.put("name", premission.getName());
                Long pid = 0l;
                if (premission.getParent() != null) {
                    pid = premission.getParent().getId();
                }
                map.put("parentid", pid);
                map.put("id", premission.getId());
                map.put("route", premission.getUrl());
                boolean rol = rolePremissionList.contains(premission);
                if (rol) {
                    map.put("ischeck", 1);//证明在里面
                } else {
                    map.put("ischeck", 0);//不在里面
                }
                json.add(map);
            });
            mv.addObject("json", json);
        }
        mv.addObject("role", JSON.toJSONString(role));
        return mv;
    }

    @PostMapping(SAVE_ROLE_PERMISSION_URL)
    @RequiresPermissions(SAVE_ROLE_PERMISSION_URL)
    public String permission(Long[] permissionId, Long roleId) {
        if (Objects.nonNull(roleId)) {
            SysRole role = roleRepository.getOne(roleId);
            List<SysPermission> permissions = sysPermissionRepository.findAllById(Arrays.asList(permissionId));
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
        return "redirect:permission?ids=" + roleId;
    }


    @GetMapping(SAVE_URL)
    @RequiresPermissions(SAVE_URL)
    public String toSave(Model m) {
        addURL(m);
        return "sys/role/roleadd";
    }

    @PostMapping(SAVE_URL)
    @RequiresPermissions(SAVE_URL)
    public String save(SysRole sysRole) {
//        addURL(m);
        sysRole.setUpdataTime(new Date());
        sysRole.setCreatedTime(new Date());
        roleRepository.saveAndFlush(sysRole);
        return BaseController.REDIRECT + LIST_URL;
    }

    @RequestMapping(REMOVE_URL)
    @RequiresPermissions(REMOVE_URL)
    public String delete(Long id, String ids) {
        if (id != null) {
            roleRepository.deleteById(id);
        } else if (!ToolStr.isEmpty(ids)) {
            String[] strid = ids.split(",");
            List<Long> listid = Arrays.stream(strid).map(tmp -> Long.parseLong(tmp)).collect(Collectors.toList());
            roleRepository.deleteAllById(listid);
        }
        return BaseController.REDIRECT + LIST_URL;
    }


}
