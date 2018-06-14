package com.sdyijia.modules.sys.repository;

import com.sdyijia.modules.sys.bean.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<SysPermission, Long> {

    List findAllByParentIdOrderByIdAsc(Long id);

    void deleteAllByParentId(Long parentId);

}
