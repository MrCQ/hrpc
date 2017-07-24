package com.hrpc.rpc.parallel;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcThreadPool {
    public static ThreadPoolExecutor getExecutor(int theads, int queues){
        String name = "RpcThreadPool";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(theads, theads, 0, TimeUnit.MILLISECONDS,
                createBlockingQueue(queues), new RpcThreadFactory(name, true), createPolicy());
        return executor;
    }

    private static BlockingQueue<Runnable> createBlockingQueue(int queues){
        return new LinkedBlockingQueue<>();
    }

    private static RejectedExecutionHandler createPolicy(){
        return new ThreadPoolExecutor.AbortPolicy();
    }

}
