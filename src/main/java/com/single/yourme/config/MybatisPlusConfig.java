package com.single.yourme.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus3配置类
 *
 * @author 1999single
 * @date 2019-11-25 19:16
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 性能分析拦截器，不建议生产使用 用来观察 SQL 执行情况及执行时长, 默认dev,staging 环境开启
     * @author 1999single
     * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
     */
//    @Bean
//    @Profile({"dev", "test"})
//    public PerformanceInterceptor performanceInterceptor(){
//
//        //启用性能分析插件, SQL是否格式化 默认false,此处开启
//        return new PerformanceInterceptor().setFormat(true);
//    }


    /**
     * 分页插件
     * @author 1999single
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
