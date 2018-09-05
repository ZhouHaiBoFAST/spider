package com.spider.dubbo.serializer;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author liuzhongkai
 */
public class KryoCollection {

    private Kryo kryo;

    public KryoCollection(Kryo kryo) {
        this.kryo = kryo;
    }

    public Kryo getKryo() {
        return kryo;
    }

    public void setKryo(Kryo kryo) {
        this.kryo = kryo;
    }
}
