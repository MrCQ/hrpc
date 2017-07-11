package com.hrpc.rpc.spring.handler;

import com.google.common.io.CharStreams;
import com.hrpc.rpc.spring.parser.RpcReferenceBeanParser;
import com.hrpc.rpc.spring.parser.RpcRegistryBeanParser;
import com.hrpc.rpc.spring.parser.RpcServiceBeanParser;
import com.hrpc.rpc.spring.parser.RpcInteceptorBeanParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {
    static {
        Resource resource = new ClassPathResource("HRPC-log.txt");

        if(resource.exists()){
            try {
                Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
                String text = CharStreams.toString(reader);
                System.out.println(text);
                reader.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("....");
        }
    }

    @Override
    public void init() {
        registerBeanDefinitionParser("service", new RpcServiceBeanParser());
        registerBeanDefinitionParser("inteceptor", new RpcInteceptorBeanParser());
        registerBeanDefinitionParser("reference", new RpcReferenceBeanParser());
        registerBeanDefinitionParser("registry", new RpcRegistryBeanParser());
    }
}