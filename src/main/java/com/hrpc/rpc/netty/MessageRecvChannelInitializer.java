package com.hrpc.rpc.netty;

import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;

/**
 * Created by changqi on 2017/7/11.
 */
public class MessageRecvChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcRecvSerializeFrame frame = null;

    MessageRecvChannelInitializer(Map<String, Object> handlerMap){ frame = new RpcRecvSerializeFrame(handlerMap); }

    MessageRecvChannelInitializer buildRpcSerializeProtocol(RpcSerializeProtocol protocol){
        this.protocol = protocol;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        frame.select(protocol, pipeline);
    }
}
