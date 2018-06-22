package com.sdyijia.modules.sys.repository;

import com.sdyijia.modules.sys.bean.SysRole;
import com.sdyijia.modules.sys.bean.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
    /**
     * 通过username查找用户信息;
     */
    SysUser findByUsername(String username);


    Page<SysUser> findByNameLike(String name, Pageable pageable);

    List<SysUser> findByName(String name);

    List<SysUser> querySysUserByRoleListCode(String code);


}
