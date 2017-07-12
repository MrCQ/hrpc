package com.hrpc.rpc.netty;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.hrpc.rpc.core.RpcSystemConfig;
import com.hrpc.rpc.parallel.RpcThreadPool;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by changqi on 2017/7/12.
 */
@Data
@NoArgsConstructor
public class RpcServerLoader {
    private static volatile RpcServerLoader rpcServerLoader = null;
    private int parallel = RpcSystemConfig.PARALLEL * 2;
    private int threadNums = RpcSystemConfig.SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS;
    private int queueNums = RpcSystemConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS;

    private ListeningExecutorService threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) RpcThreadPool.getExecutor(threadNums, queueNums));

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

    public void load(String serverAddr, RpcSerializeProtocol protocol){

    }
}
