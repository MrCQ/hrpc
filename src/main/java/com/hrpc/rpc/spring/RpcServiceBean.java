package com.hrpc.rpc.spring;


import com.hrpc.rpc.netty.MessageRecvExecutor;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Data
public class RpcServiceBean implements ApplicationContextAware, ApplicationListener{
    private String interfaceName;
    private String ref;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MessageRecvExecutor.getInstance().register2HandlerMap(interfaceName, applicationContext.getBean(ref));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
