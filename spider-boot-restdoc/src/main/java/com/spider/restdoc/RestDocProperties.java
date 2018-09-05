package com.spider.restdoc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * restdoc 配置信息
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.restdoc")
public class RestDocProperties {

    private String apiTitle;

    private String apiVersion;

    private String basePackage;

    public String getApiTitle() {
        return apiTitle;
    }

    public void setApiTitle(String apiTitle) {
        this.apiTitle = apiTitle;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
