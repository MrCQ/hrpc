package com.hrpc.rpc.spring;

import com.hrpc.rpc.netty.MessageRecvExecutor;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;

/**
 * Created by changqi on 2017/7/11.
 */

@Data
public class RpcServerBean implements InitializingBean, DisposableBean {
    private String port;
    private String protocol;

    @Override
    public void destroy() throws Exception {
        MessageRecvExecutor.getInstance().stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageRecvExecutor executor = MessageRecvExecutor.getInstance();
        executor.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        executor.setPort(port);
        executor.setSerializeProtocol(Enum.valueOf(RpcSerializeProtocol.class, "HESSIANSERIALIZE"));

        executor.start();
    }
}
