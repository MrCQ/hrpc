package com.hrpc.rpc.serialize.hessian;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by changqi on 2017/7/13.
 */
public class HessianEncoder extends MessageToByteEncoder<Object>{
    private HessianCodecUtil codecUtil;

    public HessianEncoder(HessianCodecUtil codecUtil){
        this.codecUtil = codecUtil;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        codecUtil.encode(byteBuf, obj);
    }
}
