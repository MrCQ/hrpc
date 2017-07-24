package com.hrpc.rpc.netty;

import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by changqi on 2017/7/11.
 */
public class MessageRecvChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcRecvSerializeAdaptor adaptor = null;

    MessageRecvChannelInitializer(){
        adaptor = new RpcRecvSerializeAdaptor();
    }

    MessageRecvChannelInitializer buildRpcSerializeProtocol(RpcSerializeProtocol protocol){
        this.protocol = protocol;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        adaptor.select(protocol, pipeline);
    }
}
