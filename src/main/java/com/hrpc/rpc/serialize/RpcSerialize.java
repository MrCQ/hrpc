package com.hrpc.rpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by changqi on 2017/7/14.
 */
public interface RpcSerialize {
    void serialize(OutputStream outputSteam, Object object) throws IOException;

    Object deserialize(InputStream inputStream) throws IOException;
}
