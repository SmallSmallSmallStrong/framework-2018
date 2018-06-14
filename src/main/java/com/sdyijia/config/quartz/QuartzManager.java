package com.sdyijia.config.quartz;


import com.sdyijia.config.quartz.bean.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Objects;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;


/**
 * Quartz调度管理器
 * 注：请调用时自行注意空指针异常 有很多地方没有检查
 *
 * @author sz
 */
public class QuartzManager {

    /**
     * @param sched       调度器
     * @param scheduleJob ScheduleJob
     * @Description: 添加一个定时任务，并启动
     * @Title: QuartzManager.java
     */
    public static void addJob(Scheduler sched, ScheduleJob scheduleJob, Class<? extends Job> cls) {
        try {
            if (Objects.isNull(sched))
                sched = StdSchedulerFactory.getDefaultScheduler();
            if (scheduleJob.getJobStatus() != null && scheduleJob.getJobStatus() == 1) {
                TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
                JobDetail jobDetail = newJob(cls).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
                jobDetail.getJobDataMap().put("scheduleJob", scheduleJob); //???????
                CronScheduleBuilder scheduleBuilder = cronSchedule(scheduleJob.getCronExpression());
                //按cronExpression表达式构建一个新的trigger(触发器)
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withSchedule(scheduleBuilder).build();
                sched.scheduleJob(jobDetail, trigger);
                // 启动
                if (!sched.isShutdown()) {
                    sched.start();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param sched       调度器 可为null 使用默认的调度器
     * @param scheduleJob 不获取id
     * @Description: 修改一个任务的触发时间
     * @Title: QuartzManager.java
     */
    public static void modifyJobTime(Scheduler sched, ScheduleJob scheduleJob) throws SchedulerException {
        if (Objects.isNull(sched))
            sched = StdSchedulerFactory.getDefaultScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
                scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = cronSchedule(scheduleJob
                .getCronExpression());
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        //按新的trigger重新设置job执行
        sched.rescheduleJob(triggerKey, trigger);
        sched.start();
    }


    /**
     * 恢复一个任务（Job）
     *
     * @param sched       调度器 可为null 使用默认的调度器
     * @param scheduleJob 只获取JobName，JobGroup
     * @throws SchedulerException
     */
    public static void resumeJob(Scheduler sched, ScheduleJob scheduleJob) throws SchedulerException {
        if (Objects.isNull(sched))
            sched = StdSchedulerFactory.getDefaultScheduler();
        sched.resumeJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
    }


    /**
     * @param sched       调度器 可为null 使用默认的调度器
     * @param scheduleJob ScheduleJob对象 只获取JobName，JobGroup
     * @Description: 移除一个任务，注意：不维护数据库
     * @Title: QuartzManager.java
     */
    public static void removeJob(Scheduler sched, ScheduleJob scheduleJob) throws SchedulerException {
        if (Objects.isNull(sched)) {
            sched = StdSchedulerFactory.getDefaultScheduler();
        }
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        sched.deleteJob(jobKey);// 删除任务
    }

    /**
     * @param sched       调度器 可为null 使用默认的调度器
     * @param scheduleJob ScheduleJob对象 只获取JobName，JobGroup
     * @Description: 停止一个任务，注意：不维护数据库
     * @Title: QuartzManager.java
     */
    public static void pauseJob(Scheduler sched, ScheduleJob scheduleJob) throws SchedulerException {
        if (Objects.isNull(sched)) {
            sched = StdSchedulerFactory.getDefaultScheduler();
        }
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        sched.pauseJob(jobKey);// 停止任务
    }


    /**
     * @param sched 调度器 可为null 使用默认的调度器
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public static void start(Scheduler sched) throws SchedulerException {
        if (Objects.isNull(sched)) {
            sched = StdSchedulerFactory.getDefaultScheduler();
        }
        if (sched.isShutdown()) {
            sched.start();
        }
    }

    /**
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public static void start() throws SchedulerException {
        Scheduler sched;
        sched = StdSchedulerFactory.getDefaultScheduler();
        if (sched.isShutdown()) {
            sched.start();
        }
    }


    /**
     * @Description:关闭所有定时任务
     * @Title: QuartzManager.java
     */
    public static void shutdown() throws SchedulerException {
        Scheduler sched;
        sched = StdSchedulerFactory.getDefaultScheduler();
        if (!sched.isShutdown()) {
            sched.shutdown();
        }
    }

    /**
     * @param sched
     * @param scheduleJob
     * @throws SchedulerException
     */
    public static Trigger.TriggerState query(Scheduler sched, ScheduleJob scheduleJob) throws SchedulerException {
        if (Objects.isNull(sched)) {
            sched = StdSchedulerFactory.getDefaultScheduler();
        }
        if (scheduleJob.getJobName() != null && !"".equals(scheduleJob.getJobName().trim())) {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
                    scheduleJob.getJobGroup());
            return sched.getTriggerState(triggerKey);
        }
        return null;
    }

}