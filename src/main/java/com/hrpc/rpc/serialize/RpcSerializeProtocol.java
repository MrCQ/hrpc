package com.hrpc.rpc.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by changqi on 2017/7/11.
 */

@AllArgsConstructor
@Getter
public enum RpcSerializeProtocol {
    JDKSERIALIZE("jdknative"),
    KRYOSERIALIZE("kryo"),
    HESSIANSERIALIZE("hessian"),
    PROTOSTUFFSERIALIZE("protostuff");
    private String serializeProtocol;
}
