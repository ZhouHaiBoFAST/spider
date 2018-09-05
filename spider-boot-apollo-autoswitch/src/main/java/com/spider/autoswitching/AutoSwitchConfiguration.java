package com.spider.autoswitching;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @author liuzhongkai
 */
@Import(AutoSwitchConfigurationInitializer.class)
public class AutoSwitchConfiguration implements InitializingBean, Ordered {

    @Autowired
    private AutoSwitchingBeanRegistry registry;

    @Autowired
    private List<AutoSwitchingBean<?>> beanList;

    @Override
    public void afterPropertiesSet() {
        beanList.forEach(item -> registry.register(item));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
