package com.hrpc.rpc.interceptor;

import com.hrpc.rpc.model.MessageRequest;

/**
 * Created by changqi on 2017/7/11.
 */
public interface ServiceInteceptor {
    public void handle(MessageRequest request);
}
