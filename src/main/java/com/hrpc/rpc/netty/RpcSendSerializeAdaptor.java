package com.hrpc.rpc.netty;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.hrpc.rpc.netty.handler.HessianSendHandler;
import com.hrpc.rpc.netty.handler.NettyRpcSendHandler;
import com.hrpc.rpc.serialize.RpcSerializeAdaptor;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelPipeline;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */

@NoArgsConstructor
public class RpcSendSerializeAdaptor implements RpcSerializeAdaptor {
    private static ClassToInstanceMap<NettyRpcSendHandler> instanceMap = MutableClassToInstanceMap.create();

    static {
        instanceMap.put(HessianSendHandler.class, new HessianSendHandler());
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol){
            case PROTOSTUFFSERIALIZE:
                break;
            case HESSIANSERIALIZE:
                instanceMap.get(HessianSendHandler.class).handle(pipeline);
                break;
        }
    }
}
