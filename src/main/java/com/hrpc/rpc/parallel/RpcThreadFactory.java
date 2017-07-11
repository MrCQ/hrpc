package com.hrpc.rpc.parallel;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadNum = new AtomicInteger(1);
    private final  AtomicInteger mThreadNum = new AtomicInteger(1);

    private String prefix;
    private boolean deamonThread;

    private final ThreadGroup threadGroup;

    public RpcThreadFactory(){
        this("rpc_server_threadpool_" + threadNum.getAndIncrement(), false);
    }

    public RpcThreadFactory(String prex){
        this(prex,false);
    }

    public RpcThreadFactory(String prex, boolean deamon){
        this.prefix = StringUtils.isNotEmpty(prex) ? prex + "-thread-" : "";
        deamonThread = deamon;
        SecurityManager securityManager = System.getSecurityManager();
        threadGroup = (securityManager == null) ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + mThreadNum.getAndIncrement();
        Thread thread = new Thread(threadGroup, r, name, 0);
        thread.setDaemon(deamonThread);
        return thread;
    }

    public ThreadGroup getThreadGroup(){ return this.threadGroup; }
}
