package com.spider.autoswitching;

import com.spider.common.SpiderDefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自旋
 *
 * @author liuzhongkai
 */
public class SpinAsyncTerminateStrategy<T> implements AsyncTerminateStrategy<T> {

    private static final Logger logger = LoggerFactory.getLogger(SpinAsyncTerminateStrategy.class);

    private static final int DEFAULT_MAX_RETRIES = 10;

    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private static final int DEFAULT_BASE_SLEEP_TIME_SS = 15;

    private final Executor executor;

    private final Closeable<T> closeable;

    private final int maxRetries;

    private final TimeUnit unit;

    private final long baseSleepTime;

    private final Class<T> type;

    public SpinAsyncTerminateStrategy(Class<T> type, Closeable<T> closeable) {
        this(type, closeable, DEFAULT_MAX_RETRIES, DEFAULT_TIME_UNIT, DEFAULT_BASE_SLEEP_TIME_SS);
    }

    public SpinAsyncTerminateStrategy(Class<T> type, Closeable<T> closeable, int maxRetries, TimeUnit unit, long baseSleepTime) {
        Assert.notNull(type, "type can not be empty");
        Assert.notNull(type, "closeable can not be empty");
        this.unit = unit;
        this.maxRetries = maxRetries;
        this.closeable = closeable;
        this.baseSleepTime = baseSleepTime;
        String name = type.getSimpleName();
        this.type = type;
        //queue needless
        executor = new ThreadPoolExecutor(1, 1, 2L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new SpiderDefaultThreadFactory("spin-" + name + "terminate-pool", "terminate-" + name));
    }

    @Override
    public void terminate(T destroyObj) {
        executor.execute(() -> {
            logger.info("type {} start closing", type);
            for (int i = 1; !closeable.close(destroyObj) && i <= maxRetries; i++)
                try {
                    logger.debug("type {} closed unfinished [retries={}, maxRetries={}, baseSleepTime={}, unit={}]", type, i, maxRetries, baseSleepTime, unit);
                    unit.sleep(baseSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            logger.info("type {} closed complete", type);
        });
    }
}
