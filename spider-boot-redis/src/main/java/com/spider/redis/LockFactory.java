package com.spider.redis;

import java.util.concurrent.locks.Lock;

/**
 * @author liuzhongkai
 */
@FunctionalInterface
public interface LockFactory {

    Lock getLock(Object o);

}
