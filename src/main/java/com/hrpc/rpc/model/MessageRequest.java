package com.hrpc.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRequest implements Serializable{
    private String messageId;
    private String className;
    private String methodName;
    private Class<?>[] typeparameters;
    private Object[] parametersVal;
}
