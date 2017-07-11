package com.hrpc.rpc.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */
public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> handlerMap = new HashMap<>();

    MessageRecvHandler(Map<String, Object> map){
        handlerMap.putAll(map);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
