package com.hrpc.rpc.serialize.hessian;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by changqi on 2017/7/14.
 */
public class HessianSerializePool {
    private GenericObjectPool<HessianSerialize> hessianPool = new GenericObjectPool<HessianSerialize>(new HessianSerializeFactory());

    private static class HessianSerializePoolHolder{
        public static final HessianSerializePool pool = new HessianSerializePool();
    }

    public static HessianSerializePool getInstance(){
        return HessianSerializePoolHolder.pool;
    }

    public HessianSerialize borrow(){
        try {
            return this.hessianPool.borrowObject();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void restore(HessianSerialize serialize){
        this.hessianPool.returnObject(serialize);
    }

}
