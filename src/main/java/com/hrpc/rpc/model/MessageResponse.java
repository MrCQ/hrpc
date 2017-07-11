package com.hrpc.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageResponse implements Serializable{
    private String messageId;
    private String error;
    private Object result;
}
