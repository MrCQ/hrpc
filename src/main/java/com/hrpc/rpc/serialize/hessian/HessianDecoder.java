package com.hrpc.rpc.serialize.hessian;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by changqi on 2017/7/13.
 */
public class HessianDecoder extends ByteToMessageDecoder {
    private HessianCodecUtil codecUtil;

    public HessianDecoder(HessianCodecUtil codecUtil){
        this.codecUtil = codecUtil;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < HessianCodecUtil.MESSAGE_LENGTH){
            return;
        }

        byteBuf.markReaderIndex();

        int msgLen = byteBuf.readInt();

        if(msgLen < 0){
            channelHandlerContext.close();
        }

        if(byteBuf.readableBytes() < msgLen){
            byteBuf.resetReaderIndex();
            return;
        }
        else {
            byte[] body = new byte[msgLen];

            byteBuf.readBytes(body);

            try {
                Object obj = codecUtil.decode(body);
                list.add(obj);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
