package com.milecn.quartz.cluster.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @program: mile-quartz-cluster
 * @ClassName: DataSourceConfig
 * @description: 配置数据源
 * @author: wang.wd
 * @create: 2020-01-01 15:06:39
 */
@Configurable
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {


    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

}