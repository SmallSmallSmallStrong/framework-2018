package com.sdyijia.modules.sys.service;

import com.sdyijia.exception.SaveException;
import com.sdyijia.modules.base.service.BaseService;
import com.sdyijia.modules.sys.bean.SysPermission;
import com.sdyijia.modules.sys.bean.SysRole;
import com.sdyijia.modules.sys.repository.RoleRepository;
import com.sdyijia.modules.sys.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SysRoleService extends BaseService<RoleRepository, SysRole> {


    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    public SysRole save(SysRole role) throws SaveException {
        try {
            if (role.getId() == null || role.getId() <= 0) {
                SysRole roleList = repository.findByCode(role.getCode());
                if (roleList != null) {
                    throw new SaveException("code已存在，请重新选择code。");
                }
                return repository.save(role);
            } else {
                SysRole vo = repository.getOne(role.getId());
                if (!vo.getCode().equals(role.getCode())) {
                    SysRole roleList = repository.findByCode(role.getCode());
                    if (roleList != null) {
                        throw new SaveException("code已存在，请重新选择code。");
                    }
                }
                role.setUserInfos(vo.getUserInfos());
                role.setPermissions(vo.getPermissions());
                return repository.save(vo);
            }
        } catch (Exception e) {
            throw new SaveException(e.getMessage());
        }
    }

    /**
     * 保存更改角色权限
     *
     * @param id            SysRole角色
     * @param permissionIds 权限id
     */
    public void saveRolePermission(Long id, Long[] permissionIds) {
        SysRole role = repository.getOne(id);
        List<SysPermission> perlist = sysPermissionRepository.findAllById(Arrays.asList(permissionIds));
        role.setPermissions(perlist);
        repository.saveAndFlush(role);
        permissionService.clearAllCache();
    }

    /**
     * 根据code 查询role
     *
     * @param code
     * @return
     */
    public SysRole findRole4Code(String code) {
        return repository.findByCode(code);
    }
}
