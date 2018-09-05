package com.spider.common;

import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

/**
 * 服务 执行器
 *
 * @author liuzhongkai
 */
public class ConcurrentActuator {

    /**
     * 计算型
     */
    private static final ForkJoinPool COMPUTATIONALLY_INTENSIVE_POOL;

    /**
     * IO型-阻塞较高
     */
    private static final ForkJoinPool STRONG_IO_INTENSIVE_POOL;

    /**
     * IO型-阻塞较低
     */
    private static final ForkJoinPool WEAK_IO_INTENSIVE_POOL;

    /**
     * 固定大小的池
     */
    private static final ForkJoinPool FIXED_INTENSIVE_POOL;

    /**
     * 计算型
     *
     * @return 执行器
     */
    public static ForkJoinPool getComputationallyIntensivePool() {
        return COMPUTATIONALLY_INTENSIVE_POOL;
    }

    /**
     * IO型-阻塞较高
     *
     * @return 执行器
     */
    public static ForkJoinPool getStrongIoIntensivePool() {
        return STRONG_IO_INTENSIVE_POOL;
    }

    /**
     * IO型-阻塞较低
     *
     * @return 执行器
     */
    public static ForkJoinPool getWeakIoIntensivePool() {
        return WEAK_IO_INTENSIVE_POOL;
    }

    /**
     * 固定执行器
     *
     * @return 执行器
     */
    public static ForkJoinPool getFixedIntensivePool() {
        return FIXED_INTENSIVE_POOL;
    }

    private final static int DEFAULT_PARALLELISM = 200;

    static {
        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
        COMPUTATIONALLY_INTENSIVE_POOL = new ForkJoinPool(processors + 1, threadFactory, null, false);
        STRONG_IO_INTENSIVE_POOL = new ForkJoinPool((int) (processors / (1 - 0.9)), threadFactory, null, false);
        WEAK_IO_INTENSIVE_POOL = new ForkJoinPool((int) (processors / (1 - 0.8)), threadFactory, null, false);
        new ForkJoinPool
                (processors,
                        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                        null, true);
        int parallelism = DEFAULT_PARALLELISM;
        try {
            String pp = System.getProperty("com.spider.common.ConcurrentActuator.fixedIntensivePool.parallelism");
            parallelism = Integer.parseInt(pp);
        } catch (Exception ignore) {
            ;
        }
        FIXED_INTENSIVE_POOL = new ForkJoinPool(parallelism);
        LoggerFactory.getLogger(ConcurrentActuator.class).info("concurrent actuator start work");
    }

}
