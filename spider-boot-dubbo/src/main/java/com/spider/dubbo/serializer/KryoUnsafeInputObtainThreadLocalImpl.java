package com.spider.dubbo.serializer;

/**
 * @author liuzhongkai
 */
public class KryoUnsafeInputObtainThreadLocalImpl implements KryoUnsafeInputObtain {

    private final ThreadLocalKryoUnsafeInput kryoThreadLocal = new ThreadLocalKryoUnsafeInput();

    private static final class ThreadLocalKryoUnsafeInput extends ThreadLocal<KryoUnsafeInput> {
        @Override
        protected KryoUnsafeInput initialValue() {
            return new KryoUnsafeInput();
        }

    }

    @Override
    public KryoUnsafeInput obtain() {
        return kryoThreadLocal.get();
    }
}
