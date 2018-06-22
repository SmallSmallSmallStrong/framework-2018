package com.sdyijia.modules.sys.repository;

import com.sdyijia.modules.sys.bean.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<SysRole, Long> {

    SysRole findByCode(String code);

    List<SysRole> findAllByNameLike(String name);
}
