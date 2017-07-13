package com.hrpc.rpc.netty;

import com.hrpc.rpc.netty.handler.MessageSendHandler;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by changqi on 2017/7/12.
 */
public class MessageSendInitializeTask implements Callable<Boolean> {
    private EventLoopGroup eventLoopGroup;
    private String interfaceName;
    private String remoteAddr;
    private RpcSerializeProtocol protocol;

    public MessageSendInitializeTask(EventLoopGroup eventLoopGroup, String interfaceName, String remoteAddr, RpcSerializeProtocol protocol){
        this.eventLoopGroup = eventLoopGroup;
        this.interfaceName = interfaceName;
        this.remoteAddr = remoteAddr;
        this.protocol = protocol;
    }

    @Override
    public Boolean call() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(new InetSocketAddress(remoteAddr.substring(0, remoteAddr.indexOf(":")), Integer.valueOf(remoteAddr.substring(remoteAddr.indexOf(":")))))
                .handler(new MessageSendChannelInitializer().buildRpcSearializeProtocol(protocol));

        ChannelFuture future = bootstrap.connect();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    MessageSendHandler messageSendhandler = channelFuture.channel().pipeline().get(MessageSendHandler.class);
                    RpcServerLoader.getInstance().setInterfaceHandler(remoteAddr, messageSendhandler);
                }
                else{
                    EventLoop loop = (EventLoop) eventLoopGroup.schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                call();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });

        return true;
    }
}
