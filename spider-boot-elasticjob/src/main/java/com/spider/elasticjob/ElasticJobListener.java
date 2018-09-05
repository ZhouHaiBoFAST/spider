//package com.spider.elasticjob;
//
//import com.dangdang.ddframe.job.config.JobCoreConfiguration;
//import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
//import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
//import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
//import com.spider.elasticjob.annotation.ElasticSimpleJob;
//import com.spider.util.Strings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * elasticjob spring boot 启动监听器
// *
// * @author liuzhongkai
// */
//public class ElasticJobListener implements ApplicationListener<ApplicationReadyEvent> {
//
//
//    private ZookeeperRegistryCenter zookeeperRegistryCenter;
//
//    @Autowired
//    public ElasticJobListener(ZookeeperRegistryCenter zookeeperRegistryCenter) {
//        this.zookeeperRegistryCenter = zookeeperRegistryCenter;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
//        String[] serviceNames = applicationContext.getBeanNamesForAnnotation(Service.class);
//        String[] componentNames = applicationContext.getBeanNamesForAnnotation(Component.class);
//        Set<String> beanNameSet = new HashSet<>(serviceNames.length + componentNames.length);
//        Collections.addAll(beanNameSet, serviceNames);
//        Collections.addAll(beanNameSet, componentNames);
//        for (String beanName : beanNameSet) {
//            Object bean = applicationContext.getBean(beanName);
//            if (bean == null) continue;
//            Method[] thisMethods = bean.getClass().getDeclaredMethods();
//            for (Method thisMethod : thisMethods) {
//                ElasticSimpleJob simpleJob = thisMethod.getAnnotation(ElasticSimpleJob.class);
//                if (simpleJob == null) continue;
//                JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(simpleJob.jobName(), simpleJob.cron(), simpleJob.shardingTotalCount()).shardingItemParameters(simpleJob.shardingItemParameters()).description(simpleJob.description()).failover(simpleJob.failover()).misfire(simpleJob.misfire()).jobParameter(simpleJob.jobParameter()).build();
//                SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, bean.getClass().getCanonicalName());
//                LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(simpleJob.overwrite()).disabled(simpleJob.disabled()).monitorExecution(simpleJob.monitorExecution()).build();
//                String dataSourceRef = simpleJob.dataSource();
//                ZookeeperRegistryCenter registryCenter = applicationContext.getBean(ZookeeperRegistryCenter.class);
//                if (Strings.isNotBlank(dataSourceRef)) {
//                    if (!applicationContext.containsBean(dataSourceRef))
//                        throw new RuntimeException("not exist datasource:" + dataSourceRef);
//                    DataSource dataSource = (DataSource) applicationContext.getBean(dataSourceRef);
//                    JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
//                    SpringMethodSimpleJobScheduler jobScheduler = new SpringMethodSimpleJobScheduler(bean, thisMethod, registryCenter, liteJobConfiguration, jobEventRdbConfiguration, simpleJob.isNeedShardingContext());
//                    jobScheduler.init();
//                } else {
//                    SpringMethodSimpleJobScheduler jobScheduler = new SpringMethodSimpleJobScheduler(bean, thisMethod, registryCenter, liteJobConfiguration, simpleJob.isNeedShardingContext());
//                    jobScheduler.init();
//                }
//            }
//        }
//
//    }
//}
