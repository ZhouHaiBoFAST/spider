package com.spider.dubbo.serializer;

/**
 * @author liuzhongkai
 */
public class KryoUnsafeOutputObtainThreadLocalImpl implements KryoUnsafeOutputObtain {

    private final ThreadLocalKryoUnsafeOutput kryoThreadLocal = new ThreadLocalKryoUnsafeOutput();


    private static final class ThreadLocalKryoUnsafeOutput extends ThreadLocal<KryoUnsafeOutput> {
        @Override
        protected KryoUnsafeOutput initialValue() {
            return new KryoUnsafeOutput();
        }
    }

    @Override
    public KryoUnsafeOutput obtain() {
        return kryoThreadLocal.get();
    }
}
