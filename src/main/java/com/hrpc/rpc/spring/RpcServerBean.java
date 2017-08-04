package com.hrpc.rpc.spring;

import com.hrpc.rpc.netty.MsgRecvExecutor;
import com.hrpc.rpc.serialize.RpcSerializeProtocol;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by changqi on 2017/7/11.
 */

@Data
public class RpcServerBean implements InitializingBean, DisposableBean {
    private String port;
    private String protocol;

    @Override
    public void destroy() throws Exception {
        MsgRecvExecutor.getInstance().stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MsgRecvExecutor executor = MsgRecvExecutor.getInstance();
        executor.setIpAddress("127.0.0.1");
        executor.setPort(port);
        executor.setSerializeProtocol(Enum.valueOf(RpcSerializeProtocol.class, "HESSIANSERIALIZE"));

        executor.start();
    }
}
