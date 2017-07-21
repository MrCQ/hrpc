package com.hrpc.test;

import com.hrpc.test.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcClient {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:rpc-config-client.xml");

        HelloService service = (HelloService) context.getBean("helloService");

        System.out.println(service.hello());

        //test...

        context.destroy();
    }
}
