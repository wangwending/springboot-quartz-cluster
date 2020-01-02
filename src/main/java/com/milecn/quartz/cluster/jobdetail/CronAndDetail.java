package com.milecn.quartz.cluster.jobdetail;

import com.milecn.quartz.cluster.job.JobDemo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * @program: mile-quartz-cluster
 * @ClassName: CronAndDetail
 * @description: TODO
 * @author: wang.wd
 * @create: 2020-01-01 16:47:13
 */
@Component
public class CronAndDetail {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;



    @PostConstruct
    public void init() throws SchedulerException {
        scheduleJobs();
    }

    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //需要传递数据,就是使用JobDataMa
        //       JobDataMap jobDataMap = new JobDataMap();
        //       jobDataMap.put("jobArg", "world");
        //CheckStatusTask.class 是需要执行定时任务的类名
        JobDetail jobDetail = JobBuilder.newJob(JobDemo.class)
                //              .setJobData(jobDataMap)
                .withDescription("JobDemo")
                .withIdentity("jobDmeo", "jobDmeo")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(cronSchedule("0/1 * * * * ? "))
                .build();


        try {
            if(!scheduler.checkExists(JobKey.jobKey("jobDmeo","jobDmeo"))){
                scheduler.scheduleJob(jobDetail,trigger);
            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}