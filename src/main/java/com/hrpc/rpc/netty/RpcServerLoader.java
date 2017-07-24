package com.hrpc.rpc.netty;

import com.google.common.util.concurrent.*;
import com.hrpc.rpc.conf.RpcConfig;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.netty.handler.MessageSendHandler;
import com.hrpc.rpc.parallel.RpcThreadPool;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by changqi on 2017/7/12.
 */
@Data
@NoArgsConstructor
public class RpcServerLoader {
    private static volatile RpcServerLoader rpcServerLoader = null;
    private int parallel = RpcConfig.PARALLEL * 2;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
    private int threadNums = RpcConfig.THREAD_NUMS;
    private int queueNums = RpcConfig.QUEUE_NUMS;

    //interfaceName 2 handler map;
    private Map<String, MessageSendHandler> interface2HandlerMap = new ConcurrentHashMap<>();
    //remoteAddr -> interfaceName map:
    private Map<String, String> remoteAddr2InterfaceMap = new ConcurrentHashMap<>();

    private ListeningExecutorService threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) RpcThreadPool.getExecutor(threadNums, queueNums));

    private Map<String, RpcLock> lockMap = new ConcurrentHashMap<>();

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

        if(!remoteAddr2InterfaceMap.containsKey(serverAddr)){
            remoteAddr2InterfaceMap.put(serverAddr, interfaceName);

            lockMap.put(interfaceName, new RpcLock());

            ListenableFuture<Boolean> future = threadPoolExecutor.submit(new MessageSendInitializeTask(eventLoopGroup, interfaceName, serverAddr, protocol));

            Futures.addCallback(future, new FutureCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean res) {
                    //判断连接以及handler的状态

                    RpcLock lock = lockMap.get(interfaceName);

                    try {

                        lock.getLock().lock();

                        if (!interface2HandlerMap.containsKey(interfaceName)) {
                            lock.getHandlerCon().await();
                        }

                        if(res == true && interface2HandlerMap.containsKey(interfaceName)){
                            lock.getConnectCon().signalAll();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        lock.getLock().unlock();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
        else{
            String existedInterface = remoteAddr2InterfaceMap.get(serverAddr);
            interface2HandlerMap.put(interfaceName, interface2HandlerMap.get(existedInterface));
        }
    }

    public void setInterfaceHandler(String interfaceName, MessageSendHandler sendHandler){
        RpcLock lock = lockMap.get(interfaceName);

        try {
            lock.getLock().lock();

            interface2HandlerMap.put(interfaceName, sendHandler);

            lock.getHandlerCon().signalAll();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.getLock().unlock();
        }
    }

    public MessageSendHandler getMessageSendHandlerByInterfaceName(String interfaceName){
        RpcLock lock = lockMap.get(interfaceName);

        try {
            lock.getLock().lock();

            if(!interface2HandlerMap.containsKey(interfaceName)){
                lock.getHandlerCon().await();
            }

            return interface2HandlerMap.get(interfaceName);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.getLock().unlock();
        }

        return null;
    }

    public void unload(){
        for(MessageSendHandler handler : interface2HandlerMap.values()){
            handler.close();
        }
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }
}
