package com.hrpc.rpc.netty;


import com.google.common.reflect.Reflection;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.zookeeper.discovery.ZookeeperServiceDiscovery;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by changqi.hcq on 2017/7/11.
 */

@Data
public class MessageSendExecutor {
    private Map<String,String> remoteHandlers = new ConcurrentHashMap<>();
    private ZookeeperServiceDiscovery zookeeperServiceDiscovery;
    private String registryCenterAddr;

    static class MessageSendExecutorHolder{
        public static final MessageSendExecutor executor = new MessageSendExecutor();
    }

    public static MessageSendExecutor getInstance(){
        return MessageSendExecutorHolder.executor;
    }

    public <T> T execute(Class<T> rpcInterface){
        return (T) Reflection.newProxy(rpcInterface, new MessageSendProxy<T>(remoteHandlers.get(rpcInterface.getName())));
    }

    public void fetchServieFromRegistryCenter(String serviceName) throws GlobalException{
        if(zookeeperServiceDiscovery == null){
            zookeeperServiceDiscovery = new ZookeeperServiceDiscovery(registryCenterAddr);
        }

        String serviceProviderInfo = zookeeperServiceDiscovery.discover(serviceName);

        if(serviceProviderInfo != null){
            remoteHandlers.put(serviceName, serviceProviderInfo);
        }
        else{
            throw new GlobalException("service not found in registry center");
        }
    }
}
