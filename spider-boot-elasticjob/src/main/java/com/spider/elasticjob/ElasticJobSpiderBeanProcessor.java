package com.spider.elasticjob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.spider.elasticjob.annotation.ElasticSimpleJob;
import com.spider.spring.SpiderBeanProcessor;
import com.spider.spring.SpringContextHolder;
import com.spider.util.Strings;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * 对 elastic job 注解 bean 进行加载
 *
 * @author liuzhongkai
 */
public class    ElasticJobSpiderBeanProcessor extends SpiderBeanProcessor {


    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    public ElasticJobSpiderBeanProcessor(ZookeeperRegistryCenter zookeeperRegistryCenter) {
        this.zookeeperRegistryCenter = zookeeperRegistryCenter;
    }

    @Override
    protected void doProcessMethod(Object bean, Method method, String beanName) {
        ElasticSimpleJob simpleJob = method.getAnnotation(ElasticSimpleJob.class);
        if (simpleJob != null) initSimpleJob(bean, method, simpleJob);
    }

    public void initSimpleJob(Object bean, Method method, ElasticSimpleJob simpleJob) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(simpleJob.jobName(), simpleJob.cron(), simpleJob.shardingTotalCount()).shardingItemParameters(simpleJob.shardingItemParameters()).description(simpleJob.description()).failover(simpleJob.failover()).misfire(simpleJob.misfire()).jobParameter(simpleJob.jobParameter()).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, bean.getClass().getCanonicalName());
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(simpleJob.overwrite()).disabled(simpleJob.disabled()).monitorExecution(simpleJob.monitorExecution()).build();
        String dataSourceRef = simpleJob.dataSource();
        if (Strings.isNotBlank(dataSourceRef)) {
            ApplicationContext applicationContext = SpringContextHolder.applicationContext();
            if (!applicationContext.containsBean(dataSourceRef))
                throw new RuntimeException("not exist datasource:" + dataSourceRef);
            DataSource dataSource = (DataSource) applicationContext.getBean(dataSourceRef);
            JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
            SpringMethodSimpleJobScheduler jobScheduler = new SpringMethodSimpleJobScheduler(bean, method, zookeeperRegistryCenter, liteJobConfiguration, jobEventRdbConfiguration, simpleJob.isNeedShardingContext());
            jobScheduler.init();
        } else {
            SpringMethodSimpleJobScheduler jobScheduler = new SpringMethodSimpleJobScheduler(bean, method, zookeeperRegistryCenter, liteJobConfiguration, simpleJob.isNeedShardingContext());
            jobScheduler.init();
        }
    }
}
