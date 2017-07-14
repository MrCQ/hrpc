package com.hrpc.rpc.serialize.hessian;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by changqi on 2017/7/14.
 */
public class HessianSerializeFactory extends BasePooledObjectFactory<HessianSerialize> {
    @Override
    public HessianSerialize create() throws Exception {
        return new HessianSerialize();
    }

    @Override
    public PooledObject<HessianSerialize> wrap(HessianSerialize hessianSerialize) {
        return new DefaultPooledObject<HessianSerialize>(hessianSerialize);
    }
}
