package com.spider.dubbo.serializer;

import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author liuzhongkai
 */
public class KryoUnsafeInput implements ObjectInput {

    private final static KryoAndInputObtain obtain = KryoAndInputObtain::createKryoAndInputCollection;

    private final KryoAndInputCollection collection;

    public KryoUnsafeInput() {
        collection = obtain.obtain();
    }


    public KryoUnsafeInput setInputStream(InputStream stream) {
        collection.getInput().setInputStream(stream);
        return this;
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        Input input = collection.getInput();
        Kryo kryo = collection.getKryo();
        return kryo.readClassAndObject(input);
    }

    @Override
    public <T> T readObject(Class<T> clazz) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    @Override
    public <T> T readObject(Class<T> clazz, Type type) throws IOException, ClassNotFoundException {
        return readObject(clazz);
    }

    @Override
    public boolean readBool() throws IOException {
        return collection.getInput().readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return collection.getInput().readByte();
    }

    @Override
    public short readShort() throws IOException {
        return collection.getInput().readShort();
    }

    @Override
    public int readInt() throws IOException {
        return collection.getInput().readInt();
    }

    @Override
    public long readLong() throws IOException {
        return collection.getInput().readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return collection.getInput().readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return collection.getInput().readDouble();
    }

    @Override
    public String readUTF() throws IOException {
        return collection.getInput().readString();
    }

    @Override
    public byte[] readBytes() throws IOException {
        Input input = collection.getInput();
        int len = input.readInt();
        if (len < 0) {
            return null;
        }
        return input.readBytes(len);
    }

}
