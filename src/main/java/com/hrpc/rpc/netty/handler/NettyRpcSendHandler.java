package com.hrpc.rpc.netty.handler;

import io.netty.channel.ChannelPipeline;

import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */
public interface NettyRpcSendHandler {
    public void handle(Map<String, Object> handlerMap, ChannelPipeline pipeline);
}
