package com.hrpc.rpc.spring;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class RpcServiceBean implements ApplicationContextAware, ApplicationListener{
    private String interfaceName;
    private String ref;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
