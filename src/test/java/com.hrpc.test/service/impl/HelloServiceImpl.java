package com.hrpc.test.service.impl;

import com.hrpc.test.service.HelloService;

/**
 * Created by changqi on 2017/7/11.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void hello() {
        System.out.println("hello world");
    }
}
