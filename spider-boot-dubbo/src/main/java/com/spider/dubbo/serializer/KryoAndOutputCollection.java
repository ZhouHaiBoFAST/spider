package com.spider.dubbo.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author liuzhongkai
 */
public class KryoAndOutputCollection extends KryoCollection {

    private Output output;

    public KryoAndOutputCollection(KryoCollection collection, Output output) {
        super(collection.getKryo());
        this.output = output;
    }

    public KryoAndOutputCollection(Kryo kryo, Output output) {
        super(kryo);
        this.output = output;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
