package com.sdyijia.config.quartz;

import com.sdyijia.config.quartz.bean.ScheduleJob;
import com.sdyijia.config.quartz.dao.CronRepository;
import com.sdyijia.config.quartz.job.HelloJob;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.sdyijia.config.quartz.QuartzManager.*;


/**
 * 时间调度管理器
 */
@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private CronRepository cronRepository;

    //加入Qulifier注解，通过名称注入bean
//    @Autowired
//    @Qualifier("Scheduler")
    private Scheduler scheduler;

    private static Logger log = LoggerFactory.getLogger(JobController.class);

    /**
     * 添加 helloJob任务
     *
     * @param job
     * @throws Exception
     */
    @PostMapping(value = "/addhellojob")
    @ResponseBody
    public String addjob(ScheduleJob job) {
        try {
            //存数据库
            if (job != null && job.getJobName() != null && job.getJobGroup() != null && job.getCronExpression() != null) {
                //TODO 使用正则判断job.getCronExpression()的格式是否正确
                if (job.getJobStatus() == null) {
                    //设置为qidong
                    job.setJobStatus(1);
                }
                //此处调用了QuartzManager addJob
                cronRepository.save(job);
                addJob(null, job, HelloJob.class);
                return "success";
            }
        } catch (Exception e) {
            return "fail";
        }
        return  "fail";
        //TODO 根据 group属性用以区分是哪个job或者说只要是group相同就执行想相同的job
        //建议：写成枚举的形式将group 跟 job 一一对应起来
    }


    @GetMapping(value = "/addhellojob")
    public String addjob() {
        return "cron/addhellojob";
    }


    /**
     * 停止
     *
     * @param name
     * @param group
     * @throws Exception
     */
    @GetMapping("/pausejob")
    public void pausejob(String name, String group) throws Exception {
        ScheduleJob job = new ScheduleJob();
        job.setJobGroup(group);
        job.setJobName(name);
        pauseJob(null, job);
    }

    @PostMapping(value = "/pausejob")
    public void pausejob(ScheduleJob job) throws Exception {
        if (job != null && job.getJobName() != null && job.getJobGroup() != null) {
            pauseJob(null, job);
        }
    }
//    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
//        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
//    }

    /**
     * 恢复
     *
     * @param name
     * @param group
     * @throws Exception
     */
    @GetMapping(value = "/resumejob")
    public void resumejob(String name, String group) throws Exception {
        ScheduleJob job = new ScheduleJob();
        job.setJobName(name);
        job.setJobGroup(group);
        resumejob(job);
    }

    @PostMapping(value = "/resumejob")
    public void resumejob(ScheduleJob job) throws Exception {
        if (job != null && job.getJobGroup() != null && job.getJobName() != null) {
            if (!"".equals(job.getJobName().trim()) && !"".equals(job.getJobGroup().trim())) {
                resumeJob(null, job);
            }
        }
    }

    /**
     * 更新定时器<br/>
     * 只用于更新时间;<br/>
     * 如果需要更新任务请执行addJob()填写相同的name，group即可;
     *
     * @throws Exception
     */
    @GetMapping(value = "/updata")
    public void upadataCron(String name, String group, String cron) throws Exception {
        ScheduleJob job = new ScheduleJob(name, group, null, cron);
        upadataJobOrCron(job);
    }

    @PostMapping(value = "/updata")
    public void upadataJobOrCron(ScheduleJob job) throws Exception {
        if (job != null && job.getJobGroup() != null && job.getJobName() != null && job.getCronExpression() != null) {
            if (!"".equals(job.getJobGroup().trim()) && !"".equals(job.getJobName().trim()) && !"".equals(job.getCronExpression().trim())) {
                modifyJobTime(null, job);
            }
        }
    }

    @GetMapping("/deletejob")
    public void deletejob(String name, String group) throws Exception {
        ScheduleJob job = new ScheduleJob(name, group, null, null);
        jobdelete(job);
    }

    @PostMapping(value = "/deletejob")
    public void jobdelete(ScheduleJob job) throws Exception {
        if (job != null && job.getJobGroup() != null && job.getJobName() != null) {
            if (!"".equals(job.getJobGroup().trim()) && !"".equals(job.getJobName().trim())) {
                removeJob(null, job);
            }
        }
    }

    /**
     * 查询数据库保存的定时器
     *
     * @param name
     * @param group
     * @param status
     * @return
     */
    @GetMapping(value = "/querydbjob")
    public Map<String, Object> querydbjob(String name, String group, String status) {
        List<ScheduleJob> joblist = cronRepository.findAll();
        return null;
    }

    /**
     * 查询所有跑着的定时器
     *
     * @param name
     * @param group
     * @param status
     * @return
     */
    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(String name, String group, String status) {

        return null;
    }
}