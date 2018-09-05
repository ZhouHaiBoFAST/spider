package com.spider.elasticjob.annotation;

import java.lang.annotation.*;

/**
 * @author liuzhongkai
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElasticSimpleJob {

    String cron();

    String jobName();

    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";

    String jobParameter() default "";

    String dataSource() default "";

    String description() default "";

    boolean disabled() default false;

    boolean overwrite() default false;

    boolean failover() default false;

    boolean misfire() default true;

    boolean monitorExecution() default true;

    boolean isNeedShardingContext() default false;
}
