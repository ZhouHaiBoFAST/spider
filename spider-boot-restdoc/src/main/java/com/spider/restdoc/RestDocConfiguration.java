package com.spider.restdoc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * restdoc 注册
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnProperty(name = "spider.restdoc.base-package")
@EnableConfigurationProperties({RestDocProperties.class})
@Import({Swagger2DocumentationConfiguration.class, SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class RestDocConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "docket")
    public Docket docket(RestDocProperties properties) {
        ApiInfo apiInfo = new ApiInfoBuilder().title(properties.getApiTitle()).version(properties.getApiVersion()).build();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select().apis(RequestHandlerSelectors.basePackage(properties.getBasePackage())).build();
    }

}

