package com.hrpc.rpc.model;

import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.spring.RpcServiceBean;

/**
 * Created by changqi on 2017/7/11.
 */
public interface ServiceRegistry {
    boolean register(String nodePath) throws GlobalException;
    boolean unRegister(String nodePath) throws GlobalException;
}
