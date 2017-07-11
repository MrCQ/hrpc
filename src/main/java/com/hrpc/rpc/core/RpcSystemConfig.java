package com.hrpc.rpc.core;

public class RpcSystemConfig {
    public static final int SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS = Integer.getInteger("rpc_default_thread_nums", 16);
    public static final int SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS = Integer.getInteger("rpc_default_queue_nums", -1);

    public static final int PARALLEL = Math.min(2, Runtime.getRuntime().availableProcessors());
}
