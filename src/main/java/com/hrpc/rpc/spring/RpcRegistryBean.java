package com.hrpc.rpc.spring;

import com.hrpc.rpc.netty.MessageRecvExecutor;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by changqi on 2017/7/11.
 */

@Data
public class RpcRegistryBean implements InitializingBean, DisposableBean {
    private String ipAddress;
    private String port;
    private String protocol;

    @Override
    public void destroy() throws Exception {
        MessageRecvExecutor.getInstance().stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageRecvExecutor executor = MessageRecvExecutor.getInstance();
        executor.setIpAddress(ipAddress);
        executor.setPort(port);
        executor.setSerializeProtocol(Enum.valueOf(RpcSerializeProtocol.class, protocol));

        executor.start();
    }
}
