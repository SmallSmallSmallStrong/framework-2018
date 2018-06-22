package com.sdyijia.modules.sys.bean;

import com.sdyijia.modules.base.bean.Base;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.*;

@Entity
public class SysPermission extends Base {
    /**
     * 排序
     */
    private Integer sort;
    private String name;//名称.
    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    @ManyToOne
    private SysPermission parent; //父编号
    @OneToMany
    @Cascade(value = {REMOVE})
    private List<SysPermission> children = new ArrayList<>(); //子编号
    private Integer level;//等级
    private Boolean available = Boolean.FALSE;//是否可用
    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;
    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 是否左侧显示
     */
    private boolean showLeft;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public SysPermission getParent() {
        return parent;
    }

    public void setParent(SysPermission parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public boolean getShowLeft() {
        return showLeft;
    }

    public void setShowLeft(boolean showLeft) {
        this.showLeft = showLeft;
    }

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysPermission)) return false;
        SysPermission that = (SysPermission) o;
        return Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
