package com.spider.elasticjob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.job.util.AopTargetUtils;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 基于spring boot 注解 Simple 的 作业器
 *
 * @author liuzhongkai
 */
public class SpringMethodSimpleJobScheduler extends JobScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringMethodSimpleJobScheduler.class);

    private final ElasticJob elasticJob;

    public SpringMethodSimpleJobScheduler(Object elasticJob, Method method, CoordinatorRegistryCenter regCenter, LiteJobConfiguration jobConfig, boolean isNeedShardingContext, ElasticJobListener... elasticJobListeners) {
        super(regCenter, jobConfig, getTargetElasticJobListeners(elasticJobListeners));
        this.elasticJob = toSimpleJob(elasticJob, method, isNeedShardingContext);
    }

    public SpringMethodSimpleJobScheduler(Object elasticJob, Method method, CoordinatorRegistryCenter regCenter, LiteJobConfiguration jobConfig, JobEventConfiguration jobEventConfig, boolean isNeedShardingContext, ElasticJobListener... elasticJobListeners) {
        super(regCenter, jobConfig, jobEventConfig, getTargetElasticJobListeners(elasticJobListeners));
        this.elasticJob = toSimpleJob(elasticJob, method, isNeedShardingContext);
    }


    private static SimpleJob toSimpleJob(Object obj, Method method, boolean isNeedShardingContext) {
        return shardingContext -> {
            try {
                if (isNeedShardingContext)
                    method.invoke(obj, shardingContext);
                else
                    method.invoke(obj);
            } catch (Exception e) {
                LOGGER.error("elastic-job The call execution failed exception:{}", e);
                throw new RuntimeException(e);
            }
        };
    }

    private static ElasticJobListener[] getTargetElasticJobListeners(ElasticJobListener[] elasticJobListeners) {
        ElasticJobListener[] result = new ElasticJobListener[elasticJobListeners.length];

        for (int i = 0; i < elasticJobListeners.length; ++i) {
            result[i] = (ElasticJobListener) AopTargetUtils.getTarget(elasticJobListeners[i]);
        }

        return result;
    }

    @Override
    protected Optional<ElasticJob> createElasticJobInstance() {
        return Optional.fromNullable(elasticJob);
    }

}
