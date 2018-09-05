package com.spider.redis;

import com.spider.util.SegmentLock;

import java.util.concurrent.locks.Lock;

/**
 * 单个进程 lock
 *
 * @author liuzhongkai
 */
public class JVMLockFactory implements LockFactory {

    private final SegmentLock segmentLock;

    public JVMLockFactory() {
        this.segmentLock = new SegmentLock();
    }

    public JVMLockFactory(SegmentLock segmentLock) {
        this.segmentLock = segmentLock;
    }

    @Override
    public Lock getLock(Object o) {
        return segmentLock.getLock(o.toString());
    }
}
