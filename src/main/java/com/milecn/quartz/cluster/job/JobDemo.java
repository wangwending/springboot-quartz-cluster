package com.milecn.quartz.cluster.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @program: mile-quartz-cluster
 * @ClassName: JobDemo
 * @description: TODO
 * @author: wang.wd
 * @create: 2020-01-01 15:34:11
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class JobDemo implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("task scheduling test!!!");
    }
}