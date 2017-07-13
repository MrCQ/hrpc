package com.hrpc.rpc.netty.handler;

import com.hrpc.rpc.core.MessageCallback;
import com.hrpc.rpc.model.MessageRequest;
import com.hrpc.rpc.model.MessageResponse;
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
public class MessageSendHandler extends ChannelInboundHandlerAdapter {

    private Map<String, MessageCallback> callbackMap = new ConcurrentHashMap<>();

    private volatile Channel channel = null;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        MessageResponse response = (MessageResponse) msg;
        if(callbackMap.containsKey(response.getMessageId())){
            MessageCallback callback = callbackMap.remove(response.getMessageId());

            callback.over(response);
        }
    }

    public void close(){
        this.channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public MessageCallback sendRequest(MessageRequest request){
        MessageCallback callback = new MessageCallback(request);
        callbackMap.put(request.getMessageId(), callback);
        channel.writeAndFlush(request);
        return callback;
    }
}
