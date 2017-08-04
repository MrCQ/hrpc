package com.hrpc.rpc.netty;

import com.hrpc.rpc.netty.handler.MsgSendHandler;
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
public class MsgSendInitializeTask implements Callable<Boolean> {
    private static final int MAX_RETRY_COUNT = 5;
    private int retry_count = 0;
    private EventLoopGroup eventLoopGroup;
    private String interfaceName;
    private String remoteAddr;
    private RpcSerializeProtocol protocol;

    public MsgSendInitializeTask(EventLoopGroup eventLoopGroup, String interfaceName, String remoteAddr, RpcSerializeProtocol protocol){
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
                .remoteAddress(new InetSocketAddress(remoteAddr.substring(0, remoteAddr.indexOf(":")), Integer.valueOf(remoteAddr.substring(remoteAddr.indexOf(":")+1))))
                .handler(new MsgSendChannelInitializer().buildRpcSearializeProtocol(protocol));

        ChannelFuture future = bootstrap.connect();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    RpcServerLoader.getInstance().setInterfaceHandler(interfaceName, channelFuture.channel().pipeline().get(MsgSendHandler.class));
                }
                else{
                    if(retry_count >= MAX_RETRY_COUNT){
                        return;
                    }
                    eventLoopGroup.schedule(() -> {
                            try {
                                retry_count++;
                                call();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }, 10, TimeUnit.SECONDS);
                }
            }
        });

        return true;
    }
}
