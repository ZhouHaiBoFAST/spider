package com.spider.dubbo.serializer;

import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeOutput;

/**
 * @author liuzhongkai
 */
public interface KryoAndOutputObtain extends KryoObtain<KryoAndOutputCollection> {

    int DEFAULT_BUFFER_SIZE= 512;

    int DEFAULT_MAX_BUFFER_SIZE = 2048;

    static KryoAndOutputCollection createKryoAndOutputCollection() {
        KryoObtain kryoObtain = KryoObtain::createKryoObtain;
        return new KryoAndOutputCollection(kryoObtain.obtain(), createOutput());
    }


    static Output createOutput() {
        return new UnsafeOutput(DEFAULT_BUFFER_SIZE,DEFAULT_MAX_BUFFER_SIZE);
    }

}
