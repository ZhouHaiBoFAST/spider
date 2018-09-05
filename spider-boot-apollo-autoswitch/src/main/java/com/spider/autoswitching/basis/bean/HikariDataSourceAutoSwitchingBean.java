package com.spider.autoswitching.basis.bean;

import com.google.common.collect.Sets;
import com.spider.autoswitching.AutoSwitchingBean;
import com.spider.autoswitching.bind.ConfigurationPropertiesBinding;
import com.zaxxer.hikari.HikariDataSource;


/**
 * @author liuzhongkai
 */
public class HikariDataSourceAutoSwitchingBean extends BasicsDataSourceAutoSwitchingBean<HikariDataSource> implements AutoSwitchingBean<HikariDataSource> {

    public HikariDataSourceAutoSwitchingBean(ConfigurationPropertiesBinding binding) {
        super(new SpringConfigurableEnvironmentDataSourceCreateable<>(binding, HikariDataSource.class, Sets.newHashSet("spring.datasource", "spring.datasource.hikari")), new HikariDataSourceCloseable(), HikariDataSource.class);
    }
}