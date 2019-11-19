package com.card.alumni.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author liumingyu
 * @date 2019-11-19 10:58 PM
 */
@Configuration
// 扫描对应的 Dao 接口
@MapperScan("com.card.alumni.dao")
// 启动事务管理。为什么使用 proxyTargetClass 参数，参见 https://blog.csdn.net/huang_550/article/details/76492600
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfiguration {

    @Bean("druidDataSource")
    @ConfigurationProperties("spring.datasource.druid")
    public DruidDataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    @Bean("dataSource")
    @DependsOn("druidDataSource")
    public DataSource dataSource() {
        return druidDataSource();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

}
