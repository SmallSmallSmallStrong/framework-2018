package com.sdyijia.config.quartz.dao;

import com.sdyijia.config.quartz.bean.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronRepository extends JpaRepository<ScheduleJob,Long> {

}
