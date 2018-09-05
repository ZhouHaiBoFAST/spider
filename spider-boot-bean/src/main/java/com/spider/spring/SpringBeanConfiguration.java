package com.spider.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuzhongkai
 */
@Configuration
public class SpringBeanConfiguration {


    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
