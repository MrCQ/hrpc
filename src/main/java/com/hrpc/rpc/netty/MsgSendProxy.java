package com.hrpc.rpc.netty;

import com.google.common.reflect.AbstractInvocationHandler;
import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.netty.handler.MsgSendHandler;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by changqi on 2017/7/11.
 */

@NoArgsConstructor
public class MsgSendProxy<T> extends AbstractInvocationHandler {
    private String interfaceName;
    private String providerAddr;

    public MsgSendProxy(String interfaceName, String providerAddr){
        this.interfaceName = interfaceName;
        this.providerAddr = providerAddr;
    }

    @Override
    protected Object handleInvocation(Object obj, Method method, Object[] args) throws Throwable {
        MsgRequest request = new MsgRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(args);

        //下面需要利用netty发送远程调用请求
        MsgSendHandler sendHandler = RpcServerLoader.getInstance().getMessageSendHandlerByInterfaceName(request.getClassName());
        MsgCallback callback = sendHandler.sendRequest(request);
        return callback.start();
    }
}
