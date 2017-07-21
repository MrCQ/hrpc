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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("received message : " + msg);
        MessageRequest request = (MessageRequest) msg;
        MessageResponse response = new MessageResponse();
        MessageRecvInitializeTask task = new MessageRecvInitializeTask(MessageRecvExecutor.getInstance().getHandlerMap(), request, response);
        MessageRecvExecutor.getInstance().submit(task, ctx, request, response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
