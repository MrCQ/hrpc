package com.hrpc.rpc.conf;

public class RpcConfig {
    public static final int THREAD_NUMS = 16;
    public static final int QUEUE_NUMS = 1;
    public static final int PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());
}
