package com.sdyijia.modules.sys.repository;

import com.sdyijia.modules.sys.bean.SysMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<SysMessage, Long> {

}
