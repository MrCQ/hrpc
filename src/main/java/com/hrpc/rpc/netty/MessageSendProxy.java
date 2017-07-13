package com.hrpc.rpc.netty;

import com.google.common.reflect.AbstractInvocationHandler;
import com.hrpc.rpc.core.MessageCallback;
import com.hrpc.rpc.model.MessageRequest;
import com.hrpc.rpc.netty.handler.MessageSendHandler;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by changqi on 2017/7/11.
 */

@NoArgsConstructor
public class MessageSendProxy<T> extends AbstractInvocationHandler {
    private String interfaceName;
    private String providerAddr;

    public MessageSendProxy(String interfaceName, String providerAddr){
        this.interfaceName = interfaceName;
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
        MessageSendHandler sendHandler = RpcServerLoader.getInstance().getMessageSendHandlerByInterfaceName(request.getClassName());
        MessageCallback callback = sendHandler.sendRequest(request);
        return callback.start();
    }
}
