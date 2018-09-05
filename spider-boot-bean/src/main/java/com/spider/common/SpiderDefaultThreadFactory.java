package com.spider.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程创建工厂
 *
 * @author liuzhongkai
 */
public class SpiderDefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    /**
     * 创建默认的线程工厂 名称为 spider.thread.pool-(poolSize)-thread-(threadSize)
     */
    public SpiderDefaultThreadFactory() {
        this("spider.thread.pool", "thread");
    }


    /**
     * 创建指定名称的线程工厂
     *
     * @param poolName   线程池名称
     * @param threadName 线程名称
     */
    public SpiderDefaultThreadFactory(String poolName, String threadName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = poolName + "-" + ATOMIC_INTEGER.getAndIncrement() + "-" + threadName + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
