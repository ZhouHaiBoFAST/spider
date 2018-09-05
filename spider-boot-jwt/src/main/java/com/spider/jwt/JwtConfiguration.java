package com.spider.jwt;

import com.spider.jwt.web.filter.JwtFilter;
import com.spider.jwt.web.filter.JwtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.List;

/**
 * jwt拦截配置
 *
 * @author liuzhongkai
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfiguration {

    private final List<JwtInterceptor> interceptorList;

    @Autowired(required = false)
    public JwtConfiguration(List<JwtInterceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration(JwtProperties properties) {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(createJwtFilter(properties, interceptorList));
        filterRegistration.addUrlPatterns(properties.getUrlPatterns());
        filterRegistration.setName("jwtFilter");
        return filterRegistration;
    }

    private Filter createJwtFilter(JwtProperties properties, List<JwtInterceptor> interceptorList) {
        return new JwtFilter(properties, interceptorList);
    }

    @Bean
    public JwtModel jwtModel(JwtProperties properties) {
        return new JwtModel(properties);
    }

}
