package com.hrpc.rpc.netty;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.hrpc.rpc.netty.handler.HessianRecvHandler;
import com.hrpc.rpc.netty.handler.NettyRpcRecvHandler;
import com.hrpc.rpc.serialize.RpcSerializeFrame;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelPipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcRecvSerializeFrame implements RpcSerializeFrame{

    private static ClassToInstanceMap<NettyRpcRecvHandler> handlers = MutableClassToInstanceMap.create();

    static {
        handlers.putInstance(HessianRecvHandler.class, new HessianRecvHandler());
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol){
            case JDKSERIALIZE:
                ;
                break;
            case KRYOSERIALIZE:
                ;
                break;
            case PROTOSTUFFSERIALIZE:
                ;
                break;
            case HESSIANSERIALIZE:
                handlers.getInstance(HessianRecvHandler.class).handle(pipeline);
                break;
        }
    }
}
