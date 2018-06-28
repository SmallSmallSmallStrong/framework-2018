package com.sdyijia.modules.sys.repository;

import com.sdyijia.modules.sys.bean.SysPermission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysPermissionRepository extends JpaRepository<SysPermission, Long> {

    List<SysPermission> findAllByParentIdAndNameLikeOrderByIdAsc(Long id, String name, Pageable pageable);

    List<SysPermission> findAllByLevel(Integer level, Sort sort);

    List<SysPermission> findAllByAvailableTrue();

    void deleteAllByParentId(Long parentId);



}