package com.hrpc.rpc.netty;

import com.google.common.util.concurrent.*;
import com.hrpc.rpc.conf.RpcConfig;
import com.hrpc.rpc.interceptor.ServiceInteceptor;
import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.model.MsgResponse;
import com.hrpc.rpc.parallel.RpcThreadFactory;
import com.hrpc.rpc.parallel.RpcThreadPool;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import com.hrpc.rpc.zookeeper.registry.ZookeeperServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;

import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by changqi.hcq on 2017/7/11.
 */

@Data
public class MsgRecvExecutor {
    //当前provider的IP和端口
    private String ipAddress;
    private String port;
    private RpcSerializeProtocol serializeProtocol = RpcSerializeProtocol.HESSIANSERIALIZE;

    private int parallel = RpcConfig.PARALLEL * 2;
    private int threadNums = RpcConfig.THREAD_NUMS;
    private int queueNums = RpcConfig.QUEUE_NUMS;

    private Map<String, Object> handlerMap = new ConcurrentHashMap<>();
    private Map<String, ServiceInteceptor> inteceptorMap = new ConcurrentHashMap<>();

    private static volatile ListeningExecutorService threadPoolExecutor;

    private ZookeeperServiceRegistry zookeeperServiceRegistry;

    private String registryCenterAddr;

    //创建Netty的线程池
    ThreadFactory threadFactory = new RpcThreadFactory("Netty_Thread_Factory");
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup(parallel, threadFactory, SelectorProvider.provider());

    private static class MessageRecvExecutorHolder{
        static final MsgRecvExecutor executor = new MsgRecvExecutor();
    }

    public static MsgRecvExecutor getInstance(){
        return MessageRecvExecutorHolder.executor;
    }

    //将注册的service添加进来
    public void registeService2HandlerMap(String key, Object val){
        this.handlerMap.put(key, val);
        this.register2RegistryCenter(key);
    }
    //将注册的inteceptor添加进来
    public void registerInteceptor2InteceptorMap(String key, ServiceInteceptor val){
        this.inteceptorMap.put(key, val);
    }

    public void submit(Callable<Boolean> task, final ChannelHandlerContext ctx, final MsgRequest request, final MsgResponse response){
        if(threadPoolExecutor == null){
            synchronized (MsgRecvExecutor.class){
                if(threadPoolExecutor == null){
                    threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor)RpcThreadPool.getExecutor(threadNums, queueNums));
                }
            }
        }

        ListenableFuture<Boolean> future =threadPoolExecutor.submit(task);

        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {

                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    public void start(){
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new MsgRecvChannelInitializer().buildRpcSerializeProtocol(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(ipAddress, Integer.valueOf(port)).sync();

            future.addListener((channelFuture) -> {
                if(channelFuture.isSuccess()){
                    System.out.println("Netty server has started successfully! " + ipAddress + " : " + port);
                }
                else{
                    System.out.println("Netty server failed to start!");
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        this.boss.shutdownGracefully();
        this.worker.shutdownGracefully();
        this.unregisterAllServices();
    }

    public void register2RegistryCenter(String interfaceName){
        if(zookeeperServiceRegistry == null){
            zookeeperServiceRegistry = new ZookeeperServiceRegistry(registryCenterAddr);
        }

        String providerAddr = ipAddress + ":" + port;

        zookeeperServiceRegistry.register(interfaceName + "/" + providerAddr);
    }

    public void unregisterService(String interfaceName){
        if(zookeeperServiceRegistry == null){
            return;
        }

        zookeeperServiceRegistry.unRegister(interfaceName);
    }
    public void unregisterAllServices(){
        for(String serviceName : handlerMap.keySet()){
            unregisterService(serviceName);
        }
    }
}
