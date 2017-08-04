package com.hrpc.rpc.netty.handler;

import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.model.MsgResponse;
import com.hrpc.rpc.netty.MsgRecvExecutor;
import com.hrpc.rpc.netty.MsgRecvInitializeTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by changqi on 2017/7/11.
 */
public class MsgRecvHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("received message : " + msg);
        MsgRequest request = (MsgRequest) msg;
        MsgResponse response = new MsgResponse();
        MsgRecvInitializeTask task = new MsgRecvInitializeTask(request, response);
        MsgRecvExecutor.getInstance().submit(task, ctx, request, response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
