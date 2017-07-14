package com.hrpc.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcServer {
    public static void main(String[] args){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:rpc-config-server.xml");

        //test...

        //classPathXmlApplicationContext.destroy();
    }
}
