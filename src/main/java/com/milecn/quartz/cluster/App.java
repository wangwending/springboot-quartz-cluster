package com.milecn.quartz.cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
    *@Description: quartz集群启动入口
    *@Author: wang.wd
    *@date: 2020/1/1
*/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
