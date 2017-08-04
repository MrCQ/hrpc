package com.hrpc.rpc.netty;


import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.model.MsgResponse;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MsgCallback {
    private MsgRequest request;
    private MsgResponse response;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public MsgCallback(MsgRequest request){
        this.request = request;
    }

    public Object start(){
        try {
            lock.lock();

            condition.await();

            if(this.response != null){
                return this.response.getResult();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

    public void finish(MsgResponse response){
        this.response = response;

        try {
            lock.lock();

            condition.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
