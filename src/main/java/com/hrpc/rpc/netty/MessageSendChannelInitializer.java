package com.hrpc.rpc.netty;

import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by changqi on 2017/7/12.
 */
public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcSendSerializeFrame sendSerializeFrame;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        sendSerializeFrame.select(protocol, pipeline);
    }

    public MessageSendChannelInitializer buildRpcSearializeProtocol(RpcSerializeProtocol protocol){
        this.protocol = protocol;
        return this;
    }
}
