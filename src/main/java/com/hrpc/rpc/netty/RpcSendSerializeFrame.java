package com.hrpc.rpc.netty;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.hrpc.rpc.netty.handler.HessianSendHandler;
import com.hrpc.rpc.netty.handler.NettyRpcRecvHandler;
import com.hrpc.rpc.netty.handler.NettyRpcSendHandler;
import com.hrpc.rpc.serialize.RpcSerializeFrame;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelPipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcSendSerializeFrame implements RpcSerializeFrame{
    private Map<String, Object> handlerMap = new HashMap<>();
    private static ClassToInstanceMap<NettyRpcSendHandler> instanceMap = MutableClassToInstanceMap.create();

    static {
        instanceMap.put(HessianSendHandler.class, new HessianSendHandler());
    }

    public RpcSendSerializeFrame(Map<String, Object> map){
        handlerMap.putAll(map);
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol){
            case JDKSERIALIZE:
                break;
            case PROTOSTUFFSERIALIZE:
                break;
            case KRYOSERIALIZE:
                break;
            case HESSIANSERIALIZE:
                instanceMap.get(HessianSendHandler.class).handle(handlerMap, pipeline);
                break;
        }
    }
}
