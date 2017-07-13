package com.hrpc.rpc.core;


import com.hrpc.rpc.model.MessageRequest;
import com.hrpc.rpc.model.MessageResponse;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageCallback {
    private MessageRequest request;
    private MessageResponse response;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public MessageCallback(MessageRequest request){
        this.request = request;
    }

    public Object start(){
        try {
            lock.lock();

            condition.await();

            if(this.response != null){
                return this.response.getResult();
            }
            else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void over(MessageResponse response){
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
