package com.sdyijia.config.quartz.build;

import com.sdyijia.config.quartz.dao.CronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



/**
 * 初始化  管理员用户 以及 管理员角色
 */
@Component
@Order(12)
public class QuartzInit implements CommandLineRunner {

    @Autowired
    CronRepository cronRepository;

    @Override
    public void run(String... strings) {
//        //读取数据库查询ScheduleJob表
//        List<ScheduleJob> list = new ArrayList<>();
//        list.addAll(cronRepository.findAll());
//        //遍历并且添加
//        list.forEach(scheduleJob -> {
//            if (scheduleJob.getJobStatus() != null && scheduleJob.getJobStatus() == 1) {
//                addJob(null, scheduleJob, HelloJob.class);
//            }
//        });
    }
}
