package com.hrpc.rpc.netty;

import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.HashMap;

/**
 * Created by changqi on 2017/7/12.
 */
public class MsgSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcSendSerializeAdaptor sendSerializeAdaptor = new RpcSendSerializeAdaptor();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        sendSerializeAdaptor.select(protocol, pipeline);
    }

    public MsgSendChannelInitializer buildRpcSearializeProtocol(RpcSerializeProtocol protocol){
        this.protocol = protocol;
        return this;
    }
}
