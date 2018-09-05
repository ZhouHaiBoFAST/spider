package com.spider.autoswitching.annotation;

import com.spider.autoswitching.basis.bean.HikariDataSourceAutoSwitchingBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.lang.annotation.*;

/**
 * @author liuzhongkai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutoSwitching(HikariDataSourceAutoSwitchingBean.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public @interface EnableDataSourceAutoSwitching {
}
