package com.hrpc.rpc.netty.handler;

import com.hrpc.rpc.serialize.hessian.HessianCodecUtil;
import com.hrpc.rpc.serialize.hessian.HessianDecoder;
import com.hrpc.rpc.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelPipeline;

/**
 * Created by changqi on 2017/7/11.
 */
public class HessianRecvHandler implements NettyRpcRecvHandler{
    @Override
    public void handle(ChannelPipeline pipeline) {
        HessianCodecUtil util = new HessianCodecUtil();
        pipeline.addLast(new HessianEncoder(util));
        pipeline.addLast(new HessianDecoder(util));
        pipeline.addLast(new MsgRecvHandler());
    }
}
