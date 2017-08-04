package com.hrpc.rpc.spring;

import com.hrpc.rpc.netty.MsgRecvExecutor;
import com.hrpc.rpc.netty.MessageSendExecutor;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
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

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MsgRecvExecutor.getInstance().setRegistryCenterAddr(ipAddress + ":" + port);
        MessageSendExecutor.getInstance().setRegistryCenterAddr(ipAddress + ":" + port);
    }
}
