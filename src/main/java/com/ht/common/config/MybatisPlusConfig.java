package com.ht.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2020/3/5 10:41
 * @desc mybatis-plus配置设置
 * @author yakun.shi
 */
@Configuration
@MapperScan("com.ht.common.dao")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setDbType(DbType.MYSQL);
    }

}
