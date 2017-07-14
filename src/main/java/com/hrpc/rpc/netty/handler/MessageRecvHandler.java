package com.hrpc.rpc.netty.handler;

import com.hrpc.rpc.model.MessageRequest;
import com.hrpc.rpc.model.MessageResponse;
import com.hrpc.rpc.netty.MessageRecvExecutor;
import com.hrpc.rpc.netty.MessageRecvInitializeTask;
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
        System.out.println("received message : " + msg);
        MessageRequest request = (MessageRequest) msg;
        MessageResponse response = new MessageResponse();
        MessageRecvInitializeTask task = new MessageRecvInitializeTask(handlerMap, request, response);
        MessageRecvExecutor.getInstance().submit(task, ctx, request, response);
    }
}
