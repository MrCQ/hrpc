package com.hrpc.rpc.netty;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.hrpc.rpc.netty.handler.HessianRecvHandler;
import com.hrpc.rpc.netty.handler.NettyRpcRecvHandler;
import com.hrpc.rpc.serialize.RpcSerializeAdaptor;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelPipeline;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcRecvSerializeAdaptor implements RpcSerializeAdaptor {

    private static ClassToInstanceMap<NettyRpcRecvHandler> handlers = MutableClassToInstanceMap.create();

    static {
        handlers.putInstance(HessianRecvHandler.class, new HessianRecvHandler());
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol){
            case PROTOSTUFFSERIALIZE:
                ;
                break;
            case HESSIANSERIALIZE:
                handlers.getInstance(HessianRecvHandler.class).handle(pipeline);
                break;
        }
    }
}
