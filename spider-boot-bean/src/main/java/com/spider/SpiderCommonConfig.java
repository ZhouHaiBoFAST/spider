package com.spider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot 容器配置
 *
 * @author liuzhongkai
 */
@Configuration
@SpringBootApplication
@ComponentScan(basePackageClasses = SpiderCommonConfig.class)
public class SpiderCommonConfig {
}
