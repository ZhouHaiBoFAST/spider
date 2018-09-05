package com.spider.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * redisson配置
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.redisson")
public class RedissonProperties {

    private Resource yamlFile;

    public Resource getYamlFile() {
        return yamlFile;
    }

    public void setYamlFile(Resource yamlFile) {
        this.yamlFile = yamlFile;
    }
}
