package com.hrpc.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MsgResponse implements Serializable{
    private String messageId;
    private String error;
    private Object result;
}
