package com.spider.dubbo.serializer;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.UnsafeInput;

/**
 * @author liuzhongkai
 */
public interface KryoAndInputObtain extends KryoObtain<KryoAndInputCollection> {

    int DEFAULT_BUFFER_SIZE = 2048;

    static KryoAndInputCollection createKryoAndInputCollection() {
        KryoObtain kryoObtain = KryoObtain::createKryoObtain;
        return new KryoAndInputCollection(kryoObtain.obtain(), createInput());
    }

    static Input createInput() {
        return new UnsafeInput(DEFAULT_BUFFER_SIZE);
    }
}
