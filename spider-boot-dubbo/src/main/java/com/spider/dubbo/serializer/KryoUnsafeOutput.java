package com.spider.dubbo.serializer;

import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author liuzhongkai
 */
public class KryoUnsafeOutput implements ObjectOutput {

    private final static KryoAndOutputObtain OBTAIN = KryoAndOutputObtain::createKryoAndOutputCollection;

    private final KryoAndOutputCollection collection;

    public KryoUnsafeOutput() {
        collection = OBTAIN.obtain();
    }

    public KryoUnsafeOutput setOutputStream(OutputStream stream) {
        collection.getOutput().setOutputStream(stream);
        return this;
    }

    @Override
    public void writeObject(Object value) throws IOException {
        Output output = collection.getOutput();
        Kryo kryo = collection.getKryo();
        kryo.writeClassAndObject(output, value);
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        collection.getOutput().writeBoolean(value);
    }

    @Override
    public void writeByte(byte value) throws IOException {
        collection.getOutput().writeByte(value);
    }

    @Override
    public void writeShort(short value) throws IOException {
        collection.getOutput().writeShort(value);
    }

    @Override
    public void writeInt(int value) throws IOException {
        collection.getOutput().writeInt(value);
    }

    @Override
    public void writeLong(long value) throws IOException {
        collection.getOutput().writeLong(value);
    }

    @Override
    public void writeFloat(float value) throws IOException {
        collection.getOutput().writeFloat(value);
    }

    @Override
    public void writeDouble(double value) throws IOException {
        collection.getOutput().writeDouble(value);
    }

    @Override
    public void writeUTF(String value) throws IOException {
        collection.getOutput().writeString(value);
    }

    @Override
    public void writeBytes(byte[] bytes) throws IOException {
        if (bytes == null) {
            collection.getOutput().writeInt(-1);
        } else {
            writeBytes(bytes, 0, bytes.length);
        }
    }

    @Override
    public void writeBytes(byte[] bytes, int off, int len) throws IOException {
        Output output = collection.getOutput();
        if (bytes == null) {
            output.writeInt(-1);
        } else {
            output.writeInt(len);
            output.write(bytes, off, len);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        collection.getOutput().flush();
    }
}
