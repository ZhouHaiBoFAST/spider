package com.spider.dubbo.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

/**
 * @author liuzhongkai
 */
public class KryoAndInputCollection extends KryoCollection {

    private Input input;


    public KryoAndInputCollection(KryoCollection collection, Input input) {
        super(collection.getKryo());
        this.input = input;
    }


    public KryoAndInputCollection(Kryo kryo, Input input) {
        super(kryo);
        this.input = input;
    }


    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }
}
