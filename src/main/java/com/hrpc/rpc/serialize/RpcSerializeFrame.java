package com.hrpc.rpc.serialize;

import io.netty.channel.ChannelPipeline;

/**
 * Created by changqi on 2017/7/11.
 */
public interface RpcSerializeFrame {
    void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
