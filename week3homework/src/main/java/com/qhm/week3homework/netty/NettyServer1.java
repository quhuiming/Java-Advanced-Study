package com.qhm.week3homework.netty;


import com.qhm.week3homework.netty.inbound.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer1 {
    //网关入口的server
    public static void main(String[] args) {
        //网关端口号
        int port =8801;
        EventLoopGroup bossGroup=new NioEventLoopGroup(2);
        EventLoopGroup workGroup=new NioEventLoopGroup(16);
        try {

            ServerBootstrap b =new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.SO_REUSEADDR,true)
                    .childOption(ChannelOption.SO_RCVBUF,32*1024)
                    .childOption(ChannelOption.SO_SNDBUF,32*1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInboundInitializer());//加入自定义的inboundInitializer
            //绑定端口号，建立channel
            Channel ch =b.bind(port).sync().channel();
            ch.closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
