package com.single.yourme.core.nettywebsocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: 1999single
 * @date: 2020/8/29 17:26
 */
@Slf4j
public class NioWebSocketChannelPool {

    public NioWebSocketChannelPool(){}

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}

