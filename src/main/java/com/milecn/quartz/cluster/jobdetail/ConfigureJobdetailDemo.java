package com.milecn.quartz.cluster.jobdetail;

import com.milecn.quartz.cluster.job.JobDemo;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @program: mile-quartz-cluster
 * @ClassName: ConfigureJobdetailDemo
 * @description: TODO
 * @author: wang.wd
 * @create: 2020-01-01 15:36:29
 */
@Configurable
public class ConfigureJobdetailDemo {

    @Autowired
    private DataSource dataSource;

    final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle)
                throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.setQuartzProperties(quartzProperties());
            factoryBean.setDataSource(dataSource);
            factoryBean.setOverwriteExistingJobs(true);
            factoryBean.setAutoStartup(true); // 设置自行启动
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件失败", e);
        }

        return factoryBean;
    }


    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

//    private void addJobDemo() throws Exception {
//        Scheduler scheduler = schedulerFactoryBean().getScheduler();
//
//        JobDetail jobDetail = JobBuilder.newJob(JobDemo.class)
//                .withIdentity("jobDemo", "mile-jobDemo")
//                .usingJobData("name", "jobDemo").build();
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity(String.valueOf("jobDemo"), "jobDemoTrigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();
//
//        scheduler.scheduleJob(jobDetail, trigger);
//        start();
//    }
//
//    public void start() throws Exception {
//        schedulerFactoryBean().getScheduler().start();
//    }

//    @Bean(name = "jobDemoTrigger")
//    public CronTriggerFactoryBean job1Trigger(@Qualifier("jobDemo") JobDetail jobDetail) {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(jobDetail);
//        cronTriggerFactoryBean.setCronExpression("0/1 * * * * ?");
//
//        return cronTriggerFactoryBean;
//    }
//
//    @Bean(name = "jobDemo")
//    public JobDetailFactoryBean JobDemo() {
//        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
//        jobDetailFactoryBean.setJobClass(JobDemo.class);
//        jobDetailFactoryBean.setDurability(true);
//        jobDetailFactoryBean.setRequestsRecovery(true);
//        return jobDetailFactoryBean;
//    }



}