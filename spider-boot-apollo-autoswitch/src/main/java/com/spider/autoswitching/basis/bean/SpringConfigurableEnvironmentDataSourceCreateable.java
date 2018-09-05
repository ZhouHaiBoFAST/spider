package com.spider.autoswitching.basis.bean;

import com.spider.autoswitching.Createable;
import com.spider.autoswitching.bind.ConfigurationPropertiesBinding;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 通过 spring 环境配置 创建DataSource
 *
 * @author liuzhongkai
 */
public class SpringConfigurableEnvironmentDataSourceCreateable<T extends DataSource> implements Createable<T> {

    private ConfigurationPropertiesBinding binding;

    private Set<String> prefixSet;

    private Class<T> type;


    public SpringConfigurableEnvironmentDataSourceCreateable(ConfigurationPropertiesBinding binding, Class<T> type, Set<String> prefixSet) {
        this.binding = binding;
        this.prefixSet = prefixSet;
        this.type = type;
    }

    @Override
    public T create() {
        DataSourceProperties properties = new DataSourceProperties();
        prefixSet.forEach(item -> binding.bind(properties, item));
        T dataSource = properties.initializeDataSourceBuilder().type(type).build();
        prefixSet.forEach(item -> binding.bind(dataSource, item));
        return dataSource;
    }


}
