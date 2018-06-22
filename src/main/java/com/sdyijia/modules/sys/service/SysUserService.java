package com.sdyijia.modules.sys.service;

import com.sdyijia.exception.SaveException;
import com.sdyijia.modules.sys.bean.SysRole;
import com.sdyijia.modules.sys.bean.SysUser;
import com.sdyijia.modules.sys.repository.RoleRepository;
import com.sdyijia.modules.sys.repository.UserRepository;
import com.sdyijia.utils.ECS.EncryptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysUserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PermissionService permissionService;

    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 该save方法会对pwd进行加密处理
     *
     * @return
     */
    public SysUser save(SysUser sysUser) {
        if (sysUser.getId() == null) {
            String pwd = sysUser.getPassword();
            System.out.println(pwd);
            String salt = sysUser.getSalt();
            System.out.println(salt);
            if (Objects.nonNull(sysUser) && Objects.nonNull(sysUser.getSalt()) && !"".equals(sysUser.getSalt().trim())) {
                sysUser.setPassword(EncryptionUtils.getSha512Hash(sysUser.getPassword(), sysUser.getSalt()));
            } else {
                sysUser.setPassword(EncryptionUtils.getSha512Hash(sysUser.getPassword()));
            }
            return userRepository.save(sysUser);
        } else {
            throw new SaveException("这不是更新方法");
        }
    }

    /**
     * 保存更改用户角色
     *
     * @param id      当前用户的id
     * @param roleIds 保存角色的ids数组
     */
    public SysUser saveUserRole(Long id, Long[] roleIds) {
        return saveUserRole(id, Arrays.asList(roleIds));
    }

    public SysUser saveUserRole(Long id, List<Long> roleIds) {
        SysUser user = userRepository.getOne(id);
        List<SysRole> roles = roleRepository.findAllById(roleIds);
        user.setRoleList(roles);
        permissionService.clearAllCache();
        return userRepository.save(user);
    }


    // 得到所有管理员角色的用户 code == admin
//    public List<SysUser> adminUserList() {
//        roleRepository.findByCode();
//        return userRepository.querySysUserByRoleList("admin");
//    }

    public List<SysUser> findUser4Ids(String uIds) {
        String[] strids = uIds.split(",");
        if (strids != null && strids.length > 0) {
            Long[] lonngIds = new Long[strids.length];
            for (int i = 0; i < strids.length; i++) {
                lonngIds[i] = Long.parseLong(strids[i]);
            }
            return this.findUser4Ids(lonngIds);
        } else {
            return new ArrayList<>();
        }
    }

    public List<SysUser> findUser4Ids(Long[] ids) {
        return userRepository.findAllById(Arrays.asList(ids));
    }

}
