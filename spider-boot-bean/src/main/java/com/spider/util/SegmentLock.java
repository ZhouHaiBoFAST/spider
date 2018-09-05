package com.spider.util;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分段锁
 *
 * @author liuzhongkai
 */
public class SegmentLock {

    private static final int DEFAULT_SEGMENT_SIZE = 16;

    private final Lock[] segments;

    private int segmentSize;

    /**
     * 创建默认大小为16的分段锁
     */
    public SegmentLock() {
        this(DEFAULT_SEGMENT_SIZE);
    }

    /**
     * 创建分段锁
     *
     * @param segmentSize 锁大小
     */
    public SegmentLock(int segmentSize) {
        Lock[] ls = new Lock[segmentSize];
        for (int i = 0; i < segmentSize; i++)
            ls[i] = new ReentrantLock();
        this.segmentSize = segmentSize;
        this.segments = ls;
    }

    /**
     * 获取锁
     *
     * @param key key名称
     * @return 锁
     */
    public Lock getLock(Object key) {
        if (key == null)
            throw new NullPointerException();
        return segments[key.hashCode() & (segmentSize - 1)];
    }

    /**
     * 对key名称的锁进行加锁
     *
     * @param key key名称
     */
    public void lock(Object key) {
        Lock lock = getLock(key);
        lock.lock();
    }

    /**
     * 解锁key名称的锁
     *
     * @param key key名称
     */
    public void unlock(Object key) {
        Lock lock = getLock(key);
        lock.unlock();
    }


}
