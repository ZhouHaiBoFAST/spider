package com.spider.dubbo.serializer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author liuzhongkai
 */
public class KryoSerialization implements Serialization {

    private static final KryoUnsafeInputObtain KRYO_UNSAFE_INPUT_OBTAIN = new KryoUnsafeInputObtainThreadLocalImpl();

    private static final KryoUnsafeOutputObtain KRYO_UNSAFE_OUTPUT_OBTAIN = new KryoUnsafeOutputObtainThreadLocalImpl();

    @Override
    public byte getContentTypeId() {
        return 8;
    }

    @Override
    public String getContentType() {
        return "x-application/kryo";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream outputStream) throws IOException {
        return KRYO_UNSAFE_OUTPUT_OBTAIN.obtain().setOutputStream(outputStream);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return KRYO_UNSAFE_INPUT_OBTAIN.obtain().setInputStream(inputStream);
    }
}
