package com.hrpc.rpc.netty;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by changqi on 2017/7/13.
 */

@Data
@NoArgsConstructor
public class RpcLock {
    private Lock lock = new ReentrantLock();
    private Condition connectCon = lock.newCondition();
    private Condition handlerCon = lock.newCondition();
}
