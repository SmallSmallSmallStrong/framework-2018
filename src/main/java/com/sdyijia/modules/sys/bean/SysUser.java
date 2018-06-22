package com.sdyijia.modules.sys.bean;


import com.sdyijia.config.aop.LogKey;
import com.sdyijia.modules.base.bean.Base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SysUser extends Base {

    @Column(unique = true)
    @LogKey(isUserId = true)
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;

    /**
     * 加密密码的盐。
     * 默认使用util包EncryptionUtils类的一个常量来做盐，使用该常量时该属性仍为空。
     * 当不设置该属性时则：password = （（EncryptionUtils.salt + 明文password）的加密）
     */
    private String salt;
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    @ManyToMany(fetch = FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList= new ArrayList<>();// 一个用户具有多个角色

    /**
     * 重新对盐进行了定义，用户名+salt，这样就更加不容易被破解
     * TODO 这里不甚了解为什么在MyShiroRealm->doGetAuthenticationInfo()中new SimpleAuthenticationInfo()的第三个参数需要调用本方法？
     * @return
     */
    public String getCredentialsSalt() {
        return this.username;
    }


    @Override
    public String toString() {
        return "SysUser{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                '}';
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}