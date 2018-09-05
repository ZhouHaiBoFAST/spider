package com.spider.autoswitching.annotation;

import com.spider.autoswitching.AutoSwitchingBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liuzhongkai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutoSwitchingRegistrar.class})
public @interface EnableAutoSwitching {

    Class<? extends AutoSwitchingBean>[] value();

    Class<? extends AutoSwitchingBean>[] beanClass() default {};
}
