package com.hrpc.rpc.spring;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by changqi on 2017/7/11.
 */

@Data
public class RpcReferenceBean implements FactoryBean, InitializingBean, DisposableBean{
    private String interfaceName;
    private String ref;

    @Override
    public Object getObject() throws Exception {

        return null;
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return this.getClass().getClassLoader().loadClass(interfaceName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSingleton() { return true; }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
