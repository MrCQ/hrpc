package com.hrpc.rpc.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by changqi on 2017/7/14.
 */
public class RejectedPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if(!executor.isShutdown()){
            BlockingQueue<Runnable> queue = executor.getQueue();

            int discardSize = queue.size() / 2;

            for(int i = 0 ; i < discardSize; i++){
                queue.poll();
            }

            try {
                queue.put(r);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
