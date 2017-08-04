package com.hrpc.rpc.spring;

import com.hrpc.rpc.interceptor.ServiceInteceptor;
import com.hrpc.rpc.netty.MsgRecvExecutor;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by changqi on 2017/7/11.
 */
@Data
public class RpcInteceptorBean implements ApplicationContextAware {
    private String interfaceName;
    private String ref;
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MsgRecvExecutor.getInstance().registerInteceptor2InteceptorMap(interfaceName, (ServiceInteceptor) applicationContext.getBean(ref));
    }
}
