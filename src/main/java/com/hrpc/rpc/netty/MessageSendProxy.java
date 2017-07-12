package com.hrpc.rpc.netty;

import com.google.common.reflect.AbstractInvocationHandler;
import com.hrpc.rpc.model.MessageRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by changqi on 2017/7/11.
 */
public class MessageSendProxy<T> extends AbstractInvocationHandler {
    private String providerAddr;

    public MessageSendProxy(String providerAddr){
        this.providerAddr = providerAddr;
    }

    @Override
    protected Object handleInvocation(Object obj, Method method, Object[] args) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(args);

        //下面需要利用netty发送远程调用请求

        return null;
    }
}
