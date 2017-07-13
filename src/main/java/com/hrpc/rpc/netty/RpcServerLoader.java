package com.hrpc.rpc.netty;

import com.google.common.util.concurrent.*;
import com.hrpc.rpc.core.RpcSystemConfig;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.netty.handler.MessageSendhandler;
import com.hrpc.rpc.parallel.RpcThreadPool;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by changqi on 2017/7/12.
 */
@Data
@NoArgsConstructor
public class RpcServerLoader {
    private static volatile RpcServerLoader rpcServerLoader = null;
    private int parallel = RpcSystemConfig.PARALLEL * 2;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
    private int threadNums = RpcSystemConfig.SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS;
    private int queueNums = RpcSystemConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS;

    //remoteAddr 2 sendHandler map
    private Map<String, MessageSendhandler> addr2HandlerMap = new ConcurrentHashMap<>();
    //interfaceName 2 remoteAddr map;
    private Map<String, String> interface2RemoteMap = new ConcurrentHashMap<>();

    private ListeningExecutorService threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) RpcThreadPool.getExecutor(threadNums, queueNums));

    private Lock lock = new ReentrantLock();
    private Condition connectCon = lock.newCondition();
    private Condition handlerCon = lock.newCondition();

    public static RpcServerLoader getInstance(){
        if(rpcServerLoader == null){
            synchronized (RpcServerLoader.class) {
                if(rpcServerLoader == null){
                    rpcServerLoader = new RpcServerLoader();
                }
            }
        }

        return rpcServerLoader;
    }

    public void load(String interfaceName, String serverAddr, RpcSerializeProtocol protocol) throws GlobalException{
        String[] arr = serverAddr.split(":");
        if(arr.length != 2){
            throw new GlobalException("remote address exception");
        }
        serverAddr = serverAddr.trim();
        interface2RemoteMap.putIfAbsent(interfaceName, serverAddr);
        if(!addr2HandlerMap.containsKey(serverAddr)){
            ListenableFuture<Boolean> future = threadPoolExecutor.submit(new MessageSendInitializeTask(eventLoopGroup, serverAddr, protocol));

            Futures.addCallback(future, new FutureCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    //判断连接以及handler的状态
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }
    }
}
