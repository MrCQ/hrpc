package com.hrpc.rpc.netty.handler;

import com.hrpc.rpc.netty.MsgCallback;
import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.model.MsgResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by changqi on 2017/7/11.
 */
public class MsgSendHandler extends ChannelInboundHandlerAdapter {

    private Map<String, MsgCallback> callbackMap = new ConcurrentHashMap<>();

    private volatile Channel channel = null;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        MsgResponse response = (MsgResponse) msg;
        if(callbackMap.containsKey(response.getMessageId())){
            MsgCallback cb = callbackMap.remove(response.getMessageId());
            cb.finish(response);
        }
    }

    public void close(){
        this.channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public MsgCallback sendRequest(MsgRequest request){
        MsgCallback callback = new MsgCallback(request);
        callbackMap.put(request.getMessageId(), callback);
        channel.writeAndFlush(request);
        return callback;
    }
}
