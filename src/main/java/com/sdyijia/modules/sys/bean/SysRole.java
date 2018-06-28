package com.sdyijia.modules.sys.bean;

import com.sdyijia.modules.base.bean.Base;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SysRole extends Base {

    @Column(unique = true)
    private String code; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String name; // 汉字名
    private Boolean available = Boolean.TRUE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;默认不进行级联操作
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<SysPermission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<SysUser> userInfos = new ArrayList<>();// 一个角色对应多个用户


    public String getCode() {
        return code;
    }

    public void setCode(String role) {
        this.code = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SysUser> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<SysUser> userInfos) {
        this.userInfos = userInfos;
    }
}
