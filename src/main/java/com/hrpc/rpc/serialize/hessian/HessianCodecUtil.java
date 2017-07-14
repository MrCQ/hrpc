package com.hrpc.rpc.serialize.hessian;

import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by changqi on 2017/7/13.
 */
public class HessianCodecUtil{
    public static final int MESSAGE_LENGTH = 4;

    private HessianSerializePool hessianPool = HessianSerializePool.getInstance();
    private Closer closer = Closer.create();

    public void encode(final ByteBuf out, final Object obj) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            closer.register(byteArrayOutputStream);
            HessianSerialize hessianSerialize = hessianPool.borrow();
            hessianSerialize.serialize(byteArrayOutputStream, obj);
            byte[] body = byteArrayOutputStream.toByteArray();
            out.writeInt(body.length);
            out.writeBytes(body);
            hessianPool.restore(hessianSerialize);
        } finally {
            closer.close();
        }
    }

    public Object decode(byte[] body) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            closer.register(byteArrayInputStream);
            HessianSerialize hessianSerialize = hessianPool.borrow();
            Object obj = hessianSerialize.deserialize(byteArrayInputStream);
            hessianPool.restore(hessianSerialize);
            return obj;
        } finally {
            closer.close();
        }
    }
}
